# Sora User Guide

![Image of GUI](./Ui.png)

Sora is a chatbot designed to help users efficiently manage tasks, deadlines, and productivity workflow through 
simple command-based interactions.
---
## Adding tasks
### Adding a Todo task: `todo`

Adds a simple task without any date or time.

**Format**: `todo NAME`

**Example**: `todo read book`

**Expected Output**
```
Task added! Here's what I've recorded:
[T][ ] read book
All set! You now have 1 in your list
```
---
### Adding a Deadline task: `deadline`

Adds a task that needs to be completed by a specified date and time.

**Format**: `deadline NAME /by DATE TIME`

**Example**: `deadline submit report /by 2026-02-16 11:00`

**Expected Output**
```
Task added! Here's what I've recorded:
[D][ ] submit report (by: Feb 16 2026 11:00)
All set! You now have 2 in your list
```
---
### Adding an Event task: `event`

Adds a task that occurs during a specific time period.

**Format**: `event NAME /from DATE TIME /to DATE TIME`

**Example**: `event Project Meeting /from 2026-02-16 09:00 /to 2026-02-16 12:00`

**Expected Output**
```
Task added! Here's what I've recorded:
[E][ ] Project Meeting (from: Feb 16 2026 09:00 to: Feb 16 2026 12:00)
All set! You now have 3 in your list
```
---
## Listing All Tasks: `list`

Displays all tasks in your task list.

**Format**: `list`

**Example**: `list`

**Expected Output**
```
1. [T][ ] read book
2. [D][ ] submit report (by: Feb 16 2026 11:00)
3. [E][ ] Project Meeting (from: Feb 16 2026 09:00 to: Feb 16 2026 12:00)
```
---
## Marking a Task as Done: `mark`

Marks a specific task as completed.

**Format**: `mark TASK_NUMBER`

**Example**: `mark 1`

**Expected Output**
```
Task Completed! You're making progress
 [T][X] read book
```
---
## Unmarking a Task: `unmark`

Marks a specific task as not completed.

**Format**: `unmark TASK_NUMBER`

**Example**: `unmark 1`

**Expected Output**
```
Okay! The task is still pending to be completed
  [T][ ] read book
```
---
## Deleting a Task: `delete`

Removes a task from your task list.

**Format**: `delete TASK_NUMBER`

**Example**: `delete 2`

**Expected Output**
```
Got it! Task removed
 [D][ ] submit report (by: Feb 16 2026 11:00)
All set! You now have 2 in your list
```
---
## Viewing Tasks on a Specific Date: `on`

Displays all tasks scheduled on a specific date.

**Format**: `on DATE`

**Example**: `on 2026-02-16`

**Expected Output**
```
1. [E][ ] Project Meeting (from: Feb 16 2026 09:00 to: Feb 16 2026 12:00)
```
---
## Finding Tasks: `find`

Searches for tasks containing a specific keyword. Supports **fuzzy search** using Jaro-Winkler, so approximate 
matches will also be found. 

**Format**: `find KEYWORD`

**Example**: `find meeting`

**Expected Output**
```
Here are the tasks I found matching meeting:
1. [E][ ] Project Meeting (from: Feb 16 2026 09:00 to: Feb 16 2026 12:00)
```
> `KEYWORD` is case-insensitive
---
## Exiting the program: `bye`

Exits the Sora application.

**Format**: `bye`

**Example**: `bye`

**Expected Output**
```
Oh, leaving already? Hope you have a productive day!
```
---

## Command Summary

| Command        | Format                                     | Example                                                             |
|----------------|--------------------------------------------|---------------------------------------------------------------------|
| ***Todo***     | `todo NAME`                                | `todo read book`                                                    |
| ***Deadline*** | `deadline NAME /by DATE TIME`              | `deadline submit report /by 2026-02-16 11:00`                       |
| ***Event***    | `event NAME /from DATE TIME /to DATE TIME` | `event Project Meeting /from 2026-02-16 09:00 /to 2026-02-16 12:00` |
| ***List***     | `list`                                     | `list`                                                              |
| ***Mark***     | `mark TASK_NUMBER`                         | `mark 1`                                                            |
| ***Unmark***   | `unmark TASK_NUMBER`                       | `unmark 1`                                                          |
| ***Delete***   | `delete TASK_NUMBER`                       | `delete 2`                                                          |
| ***on***       | `on DATE`                                  | `on 2026-02-16`                                                     |
| ***Find***     | `find KEYWORD`                             | `find meeting`                                                      |
| ***Exit***     | `bye`                                      | `bye`                                                               |
### Remarks
- Task number starts from 1
- Date format: `YYYY-MM-DD HH:MM` (e.g.`2026-02-16 23:59`)
- Commands are ***case-insensitive***
- Tasks are automatically saved to `./data/sora.txt`
- If the data file is corrupted or unreadable, Sora will skip that line and proceed to read the next line
---


## Fuzzy Search with Jaro-Winkler
Sora's `find` command supports ***fuzzy searching*** using the ***Jaro-Winkler similarity algorithm***, allowing 
Sora to locate tasks even if the user misspells a keyword or types a slightly different word.

### What is Jaro-Winkler? 
The **Jaro-Winkler algorithm** is a string similarity metric:
- It measures how similar two strings are.
- Returns a score between 0.0 (no similarity) and 1.0 (exact match)
- Gives extra weight to matching characters at the beginning of words, which are useful for short keywords like task 
  titles. 
> This means that mistyping `meetng` will still match `meeting` because the start of the word is correct.

### How Sora Uses it
When you type:
```
find KEYWORD
```
Sora will: 
1. Compare the `KEYWORD` with all task names
2. Look for an exact match
3. Compute a Jaro-Winkler similarity score for each task
4. Returns tasks that score above the threshold (around 0.85 similarity)

This helps to approximate misspelled words and find the correct task.

#### Example 1: Simple Typo
**Task List**:
```
1. [T][ ] read book
2. [E][ ] Project Meeting (from: Feb 16 2026 09:00 to: Feb 16 2026 12:00)
```

**Command**: `find meetng`

**Expected Output**
```
Here are the tasks I found matching meetng:
1. [E][ ] Project Meeting (from: Feb 16 2026 09:00 to: Feb 16 2026 12:00)
```
> `meetng` is a typo, but Jaro-Winkler identifies the closest match.
---
#### Example 2: Partial Match
**Task List**:
```
1. [T][ ] read book
2. [E][ ] Project Meeting (from: Feb 16 2026 09:00 to: Feb 16 2026 12:00)
```

**Command**: `find meet`

**Expected Output**
```
Here are the tasks I found matching meet:
1. [E][ ] Project Meeting (from: Feb 16 2026 09:00 to: Feb 16 2026 12:00)
```
> Even a partial String `meet` matches `meeting`
---
#### Example 3: Multiple Matches
**Task List**:
```
1. [T][ ] read book
2. [D][ ] submit CS2103 report (by: Feb 16 2026 11:00)
3. [E][ ] CS2103 Project Meeting (from: Feb 16 2026 09:00 to: Feb 16 2026 12:00)
```

**Command**: `find cs2103`

**Expected Output**
```
Here are the tasks I found matching cs2103:
1. [D][ ] submit CS2103 report (by: Feb 16 2026 11:00)
2. [E][ ] CS2103 Project Meeting (from: Feb 16 2026 09:00 to: Feb 16 2026 12:00)
```
> All tasks containing a word similar to `cs2103` are listed
---

##### Remarks 
- Fuzzy search works only for task names, not for dates or indexes
- Small typos, missing letters or partial words can still match the correct task
- The similarity threshold can be turned in the backend to control sensitivity
---
## References  
- sora.png: https://uxwing.com/cloudy-color-icon/ — free to use, no attribution required.
- user.png: https://uxwing.com/users-icon/ — free to use, no attribution required.