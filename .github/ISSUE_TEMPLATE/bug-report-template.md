---
name: Bug Report
about: Create a report to improve more
title: '[Bug]: Mismatching JDK and Jacoco versions leads to failure in GitHub workflow action'
labels: ['bug']

---

**Describe the bug**

In the integration test stage, the Jacoco maven plugin is used to generate the coverage report. At first, the Jacoco version 0.8.2 and the JDK version 23 are used. When pushing those updates to GitHub, it failed and showed error messages.


**To Reproduce**

Steps to reproduce the behavior:
1. Go to '.github/workflow/main.yml' and change JDK version for 23.
2. Go to 'pom.xml' and change Jacoco version for 0.8.2.
3. Commit and push those updates to github.
4. Unit test and integration test stages failed by showing error during loading the workflow on Github.


**Expected behavior**

The workflow action on Github must be successful.


**Screenshot**

<img width="677" alt="version_error" src="https://github.com/user-attachments/assets/95fad689-fd56-4e0d-b4cf-802cf2438c67">

