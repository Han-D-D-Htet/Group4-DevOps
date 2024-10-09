# USE CASE: 19 Produce a report on all the capital cities in a region organised by largest to smallest.

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *Data Scientist* I want *to produce a report on all the capital cities in a region organised by largest to smallest* so that *I can easily access the capital cities information in a region.*

### Scope

Company

### Level

Primary task

### Preconditions

Database includes population information of the world.

### Success End Condition

A report of all the capital cities in a region organised by largest population to smallest will be produced.

### Failed End Condition

No report is produced.

### Primary Actor

Data Scientist

### Trigger

Request population information of the world from the organisation.

## MAIN SUCCESS SCENARIO

1. Data Scientist asks for the capital cities information of a region organised by largest to smallest.
2. System extracts and sorts capital cities of a region by largest population to smallest using query.
3. System stores that sorted information in the array list and prints out as the sorted list.
4. Data Scientist can view the report for those information.

## EXTENSIONS

None

## SUB-VARIATIONS

None

## SCHEDULE

**DUE DATE**: Release 1.0