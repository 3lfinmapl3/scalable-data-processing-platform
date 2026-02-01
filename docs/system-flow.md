## V1 System Flow

1. User uploads a file using Upload Service
   
2. Upload Service :-
   	a. Stores The File on Disk
   	b. Creates a job record with status 'CREATED'
   
3. Processing Service :-
   a. Polls for new Jobs
   b. Marks jobs as 'IN\_PROGRESS'
   c. Reads file line by line
   d. Groups records into chunks
   e. For Each Chunk :-
   	1. Validate Records
   	2. Transform Records
   	3. Write Valid Records to DB
   	4. Write Invalid Records to error table
   	5. Update Progress
   f. On Completion, mark job COMPLETED
   g. On failure, mark job FAILED (so we can restart from last check point)
