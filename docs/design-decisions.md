\# V1 Design Decisions



> \*\*NOTE\*\* : This file consists off all the design and architectural decisions taken while coding this project. This document will act as a supporting medium for why certain design, change of design or technology was used and leaves not stones unturned.



1\. \*\*Dual Service System\*\* :  This will be a dual microservice based system and will have an \_UPLOAD\_ service and \_PROCESSING\_ service.

&nbsp;	

&nbsp;	\*\*Rationale\*\* : Uploading files are user facing, time sensitive and short lived while Processing those files are a long-running resource intensive process. Separating these services would allow users to upload files to the system independent of the processing service. 

&nbsp;	

&nbsp;	\*\*Alternatives Considered\*\* : Single monolithic service that handles all the processes.

&nbsp;	\*\*Trade-offs\*\* : Operational complexity gets quite high, and will require inter-process co-ordination.

---

2\. \*\*Java 21 As Runtime Platform\*\* : Java 21 will be used as the runtime for the system.

&nbsp;	

&nbsp;	\*\*Rationale\*\* : The long time support as well as new and modern concurrency features are well-suited for the I/O workload such as file streaming and database interactions.



&nbsp;	\*\*Alternatives Considered\*\* : Java 17



&nbsp;	\*\*Trade-offs\*\* : Requires newer runtime environments on deployment.



---

3\. \*\*PostgreSQL as primary Database\*\* : Use of PostgreSQL for storing job-metadata, job status, and domain data. 



&nbsp;	\*\*Rationale\*\* : PostgreSQL offers strong \_transactional guarantees\_, mature indexing capabilities, and reliable support for complex queries as well as batch inserts which are essential for consistent data ingestion.



&nbsp;	\*\*Alternatives Considered\*\* : MySQL and NoSQL 



&nbsp;	\*\*Trade-offs\*\* : Vertical scaling limits when compared with some NoSQL solutions.

---

4\. \*\*Local File System for file storage (v1)\*\* : Store uploaded files on the local file storage in V1.



&nbsp;	\*\*Rationale\*\* : Local storage simplifies file storage and helps focus learning on file processing than cloud infrastructure. The storage layer is abstracted to allow future migrations to object storage (e.g. S3).



&nbsp;	\*\*Alternatives Considered\*\* :  Cloud object storage (S3, GCS)



&nbsp;	\*\*Trade-offs\*\* : Files not shared across nodes, and are not suitable for horizontal scaling.

&nbsp;	

&nbsp;	---



5\. \*\*Chunk-Oriented processing\*\* : Process file in fixed-size chunk rather than loading entire file into memory.



&nbsp;	\*\*Rationale\*\* : Loading files chunk by chunk will help in progress tracking and minimizes memory usage while and supporting restart in-case of failures.



&nbsp;	\*\*Alternatives Considered\*\* : Load the entire file into memory at once.

&nbsp;	

&nbsp;	\*\*Trade-offs\*\* : Complicates tracking state and checkpoints.

&nbsp;		

---



6\.  \*\*Explicit out-of-scope items for V1\*\* : Exclude certain technologies from V1



&nbsp;	\*\*Out of scope Items\*\* :-

&nbsp;	1. Kafka or message brokers.

&nbsp;	2. Cloud native storage

&nbsp;	3. Kubernetes Deployment

&nbsp;	4. UI or front-end components.



&nbsp;	\*\*Rationale\*\* : Not including extra features help us focus on core processing reliability and correctness

&nbsp;	

&nbsp;	--- 

