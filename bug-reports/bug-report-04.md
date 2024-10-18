---
name: Bug Report
about: Create a bug report to improve more
title: 'Invalid output for the percentage of total population not living/living in cities for population reports'
labels: ['bug']

---

**Describe the bug**

In population reports, the percentage of total population living/not living in cities only prints 0% for all values and it is an invalid output although no specific error was encountered. The problem is that it truncates the result (thus only 0% comes out as the values are decimal less than 1 before changing to percentage) when dividing the population of cities by total population since the number types are "long".

**To Reproduce**

Steps to reproduce the behavior:
1. Go to 'PopulationData.java'.
2. Remove '(double)' in front of dividend variable in line 103 and 105.
3. Run 'App.java' again and total population will be printed with 0% percentages..


**Expected behavior**

The percentages of total population should be correctly calculated and displayed in the reports.


**Screenshot**

![bug report 4](https://github.com/user-attachments/assets/f9be7150-5aa5-4f3d-bfb1-ae08add60757)

**Additional**

To solve the bug, the dividend (population living in cities) variable type is changed to "double" (which is for decimal values) and then to get integer percentages, Math.round() method is used to round floating point numbers. By doing those, valid percentages are printed.
