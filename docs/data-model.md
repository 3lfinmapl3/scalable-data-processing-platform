# Data Model 

## Processing Job

Everything in this system boils down to one core concept, _Job_. Everything else exists just to support it. 
A _job_ represents :-

  1. A file being uploaded
  2. One processing attempt
  3. One lifecycle

## Job Status Lifecycle 

A _job_ is a stateful entity and represents these states :-
  1. `CREATED` : Once user uploads file for processing
  2. `IN_PROGRESS` : The job gets picked for reading and processing
  3. `COMPLETED` : Data has been processed on and the job is considered finished.
  4. `FAILED` : If the job gets terminated mid-processing or comes across an unrecoverable error.

## Data Handling

Upload service and Processing service will have their own set of tables maintained in PostgreSQL, owned by their respective services. 
Processing service will own a few tables _processing_job_ :

> processing_job


id (UUID, PK)

file_path (TEXT)

status (VARCHAR)

processed_records (BIGINT)

failed_records (BIGINT)

last_processed_offset (BIGINT)

created_at (TIMESTAMP)

updated_at (TIMESTAMP)


## Poison Data Handling 
Invalid data is separated from the good data and stored in a different table, this helps isolate poison data and aids with restartability.

> job_error_record

id (UUID, PK)

job_id (UUID, FK)

record_offset (BIGINT)

raw_data (TEXT)

error_message (TEXT)

created_at (TIMESTAMP)

