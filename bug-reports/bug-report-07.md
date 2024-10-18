---
name: Bug Report
about: Create a bug report to improve more
title: 'Wrong type of capital name in countries report'
labels: ['bug']

---

**Describe the bug**

While printing countries report, the capital names of the countries are displayed as codes (e.g. 1891, 211) whereas they should be the name of the capitals. Although it is not an error exactly and no error is encountered, the result is not valid.

**To Reproduce**

Steps to reproduce the behavior:
1. Go to 'CountryData.java' and go to one query for country information.
2. In the query, remove WHERE clause, 'city' table from 'FROM', and then change 'city.capital' to 'country.capital' in SELECT statememnt.
3. Run 'App.java' and capital names will be number codes although no error occurred.


**Expected behavior**

The names of the capitals of the countries should be displayed in the countries report.


**Screenshot**

![bug report 7](https://github.com/user-attachments/assets/904a399e-9134-4e40-8f9d-961f67f92ff5)

**Additional**

To solve the bug, the query statement is checked again, and the problem is that information only from 'country' table of the database is extracted and thus, capital names are not shown. The query is modified, extracting capital name from 'city' table and thus, the capital names are correctly printed.
