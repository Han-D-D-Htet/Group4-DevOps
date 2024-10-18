---
name: Bug Report 02
about: A bug for total population reports
title: '[Bug]: Using wrong variable type for total population number'
labels: ['bug']

---

**Describe the bug**

For population reports, while retrieving total population numbers from database, the error encountered, and relevant information are not printed since the type of variable is set as integer and the number of populations is longer than an integer variable type accepts in Java.

**To Reproduce**

Steps to reproduce the behavior:
1. Go to 'PopulationData.java'.
2. Change 'int' from 'long' variable type in line 97, 99, 101.
3. Change retrival method to getInt() in line 97 and 99.
4. Run 'App.java' and there will be an error.


**Expected behavior**

Appropriate type for population number should be assigned and population reports should be displayed.


**Screenshot**

![bug report 2](https://github.com/user-attachments/assets/d88d335c-0a33-4e9e-a107-5bb7063ad5aa)


**Additional**

As the number is long and out of valid range for Java integer type, "long" variable type, which is for very long numbers, is set for those population numbers and error is solved.
