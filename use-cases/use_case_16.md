# USE CASE: 16 Produce a report on the top N populated cities in a district where N is provided by the user.

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *Data Scientist* I want *to produce a report on the top N populated cities in a district where N is provided by the user* so that *I can easily access top N populated cities information in a district.*

### Scope

Company

### Level

Primary task

### Preconditions

Database includes population information of the world.

### Success End Condition

A report of the top N populated cities in a district where N is provided by the user will be produced.

### Failed End Condition

No report is produced.

### Primary Actor

Data Scientist

### Trigger

Request population information of the world from the organization.

## MAIN SUCCESS SCENARIO

1. Data Scientist asks for top N populated cities information in a district where N is provided by the user.
2. System extracts and sorts top N populated cities in a district where N is provided by the user using query.
3. System stores that sorted information in the array list and prints out as the sorted list.
4. Data Scientist can view the report for those information.

## EXTENSIONS

None

## SUB-VARIATIONS

None

## SCHEDULE

**DUE DATE**: Release 1.0