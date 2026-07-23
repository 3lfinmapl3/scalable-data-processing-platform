# Data Model 

This document describes the primary data entities used by the Scalable Data Ingestion Platform.

The primary goal of the data model is to support:

- Streaming processing of large files
- Job progress tracking
- Restartability after failures
- Isolation of invalid records
- Idempotent data ingestion

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

The processing_job table acts as the source of truth for every uploaded file.
Rather than tracking individual processing threads or requests, the system tracks the lifecycle of the processing job itself. 
This allows processing to resume safely after interruptions while also providing visibility into the current progress of a file.
> processing_job


id
Unique identifier of the processing job.

file_path
Location of the uploaded file on disk.

status
Current lifecycle state of the processing job.

processed_records
Number of successfully processed records.

failed_records
Number of invalid records skipped during processing.

last_processed_offset
Stores the last successfully processed position in the file.
This enables the job to resume processing after failures without
starting from the beginning.

created_at
Timestamp when the job was created.

updated_at
Timestamp of the latest state update.


## Poison Data Handling 
Rather than terminating the entire processing job when an invalid
record is encountered, invalid records are isolated into a separate
table.

This approach allows valid records to continue processing while
providing operators with sufficient information to inspect,
correct, and potentially reprocess failed records later.

This improves reliability and fault tolerance.

> job_error_record

id (UUID, PK)

job_id (UUID, FK)

record_offset (BIGINT)

raw_data (TEXT)

error_message (TEXT)

created_at (TIMESTAMP)

## Domain Data

Successfully processed business records are stored separately from
processing metadata.

Each processed record is associated with the processing job that
created it.

A business key will be maintained for each record to support
idempotent processing and prevent duplicate inserts during retries.

## Future Enhancements

The following capabilities are intentionally excluded from V1:

- Percentage-based progress reporting
- Distributed processing
- Cloud object storage
- Message queues
- Advanced retry strategies

These features may be introduced in later versions once the core
processing pipeline is stable.

