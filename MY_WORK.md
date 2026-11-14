# 📝 MY_WORK: Student Information, Development Log, Reflection & Answers

> This is the **only file** your instructor reads to grade Parts 3 and 4 (documentation and video). Everything you write here must be **in your own words**.

---

## 🛑 STOP: Read This Before You Do Anything Else

> ### 1️⃣ Read the whole `README.md` first
> The `README.md` in this repository contains the full instructions: how this assignment continues Assignment 1, the Chapter 5 recap, class descriptions, feature specifications, question prompts and the video script. **If you skip it, you will lose marks.**
>
> ### 2️⃣ Understand the full code before answering any question
> Open `SchedulerSimulationSync.java` and read it **from top to bottom**. You must be able to explain what `SharedStats`, `ReadyQueue`, `CpuWorker`, `RaceWindow.pause()`, `ReentrantLock` and `Semaphore` do **before** you write a single answer in Parts B and C. **Run the original starter first** (it is intentionally broken) and **save its output**: you need it for Question 1 of Part C.
>
> ### 3️⃣ Use only the code of this repository
> Work **only** on `SchedulerSimulationSync.java` from this repository. Do **not** copy or paste code from your previous assignments.
>
> ### 4️⃣ Commit many times, not once
> A single commit, or all commits made in the last hour, costs you **-0.5 mark**. See the [Commit Rules](#-commit-rules-mandatory) below.

**How to use this file:**
1. Fill in your **Student Information** (below) right now.
2. Follow the steps in the **Work Roadmap** in order.
3. Update the **Development Log** *every time* you work on the assignment, not at the end.
4. Do not delete any section header. Replace the `[...]` placeholders with your own text.

---

## 👤 Student Information

> ⚠️ **WARNING:** Fill this in first. Your name and ID must match the student ID you set in `SchedulerSimulationSync.java` (line 304) and the one you say in your video.

| Field | Your Answer |
|-------|-------------|
| **Full Name** |Danh Qasim Alosaimi |
| **Student ID** | 445052389 |
| **University Email** |445052389@std.psau.edu.sa |
| **GitHub Username** | danh-alosaimi |
| **Repository Link** | https://github.com/danh-alosaimi/OS-Assignment3-danh-alosaimi |
 
---

## 🎥 Video Link

**Video Link**: [Paste your video link here]

> ⚠️ **WARNING:** The video must be **publicly accessible** ("Anyone with the link can view") on **Google Drive**, **YouTube (Unlisted or Public)** or any other cloud file-sharing system. A private, restricted or broken link counts as a **missing video (-1 mark)**.
>
> 💡 **TIP:** Open the link in a **private/incognito window** before you submit. If it asks you to log in or request access, it is not public.
>
> 📌 **NOTE:** The link goes in **this file only** (`MY_WORK.md`), **not** in `README.md`. Name your video file `StudentID_Assignment3_Demo.mp4`. It must last **2 to 3 minutes**.

---

## 🗺️ Work Roadmap (follow in this order)

| Step | What to do | Where | Marks |
|:----:|------------|-------|:-----:|
| 0 | Read `README.md`, read the full code, **run the original starter and save its output** (it fails on purpose) | Your IDE | – |
| 1 | Fork, rename, keep the repo **PUBLIC**, set your student ID (line 304), **commit** | GitHub + code | Part 1 (1) |
| 2 | Feature 1: Mutex lock on the ready queue, **commit** | Code | Part 2 (0.25) |
| 3 | Feature 2: Mutex lock(s) on the shared statistics, **commit** | Code | Part 2 (0.25) |
| 4 | Feature 3: Counting semaphore instead of busy waiting, **commit** | Code | Part 2 (0.5) |
| 5 | Development Log (5+ entries, different dates) | This file, Part A | Part 3 (0.5) |
| 6 | Reflection (4 questions) | This file, Part B | Part 3 (0.5) |
| 7 | Technical Answers (4 questions) | This file, Part C | Part 3 (0.5) |
| 8 | Record the video, upload it, paste the link above | Video + this file | Part 4 (1.5) |
| 9 | Final check, then submit the repo link on Blackboard | Blackboard | – |

> 💡 **TIP:** Tick each step off as you go. Do not leave the log, the reflection or the video for the last day.

---

## 🔁 Commit Rules (MANDATORY)

> ### ⚠️ MANY COMMITS ARE REQUIRED. A single bulk commit is penalized (-0.5 mark).

**Minimum: 4 meaningful commits. Aim for 7 or more.**

| # | Commit | Example message |
|:-:|--------|-----------------|
| 1 | Student ID set | `Set my student ID: 441234567` |
| 2 | Feature 1: Ready queue lock | `Feature 1: Protected the ready queue with a ReentrantLock` |
| 3 | Feature 2: Statistics locks | `Feature 2: Added locks to the shared statistics counters` |
| 4 | Feature 3: Semaphore | `Feature 3: Replaced busy waiting with a counting semaphore` |
| 5 | Development log entries | `Docs: Added development log entries 1-3` |
| 6 | Reflection and answers | `Docs: Completed reflection and technical answers` |
| 7 | Video link | `Docs: Added demo video link` |

**Rules:**
- ✅ **One commit per feature.** Do not put all three features in one commit.
- ✅ **Commit after each work session**, and after each part of this file.
- ✅ **Spread your commits over different dates.** Not all in one day.
- ❌ **Do not make all commits in the last hour** before the deadline (November 14, 2026).
- ❌ **No vague messages** like `done`, `update` or `final version`.

> 💡 **TIP:** Your commit history is checked and you **show it in your video** (at least 4 commits visible). Your development log dates should match your commit dates.
>
> 💡 **TIP:** **Use VS Code** (see *Recommended Development Environment* in `README.md` for the full setup). Sign in to GitHub in VS Code, then commit from the Source Control panel (Ctrl+Shift+G) → stage → write a message → Commit → Sync/Push. You can edit and commit this file the same way. **Pushing** matters: commits that are not pushed to GitHub are invisible to the instructor.

---

# Part A: Development Log (0.5 mark)

> ⚠️ **WARNING:** Minimum **5 entries**, spread over **different dates**. Five entries written on the same day, or written all at once at the end, will lose marks and look like a copy. Entry dates should be **between the start of the assignment and the deadline (November 14, 2026)**.
>
> 💡 **TIP:** Write an entry at the **end of each work session**, while you still remember what happened. It takes 5 minutes.
>
> 💡 **TIP:** Be specific. "Worked on the code" is a weak entry. "Added a `ReentrantLock queueLock` to `ReadyQueue` and wrapped the body of `poll()` in `lock()` / `try` / `finally { unlock() }`" is a strong one.
>
> 📌 **NOTE:** Each entry needs: date and time, what you did, details, challenges, solution, and time spent. Real challenges are fine (and expected). Do not invent fake ones.

## Example Entry (do not copy it, write your own)

### Entry 1 - [November 2, 2026, 4:30 PM]
**What I did**: Forked the repository, set my student ID, and ran the original starter

**Details**:
- Forked the starter repository and renamed it
- Changed student ID on line 304 to my actual ID (441234567)
- Ran the original program: 4 CPUs started, three of them picked `P1`, then `IllegalThreadStateException` and the watchdog stopped the program
- Saved the output in a text file for Question 1
- Committed and pushed: `Set my student ID: 441234567`

**Challenges**: I did not understand why the same process was picked by three CPUs

**Solution**: Read `ReadyQueue.poll()` again and found the `RaceWindow.pause()` between reading the head and removing it

**Time spent**: 45 minutes

---

## Your Development Log

### Entry 1 - [November 11, 2026, 6:12 PM]
**What I did**:Forked the repository, set my student ID, and ran the original starter.

**Details**:Forked and renamed the repository to OS-Assignment3-danh-alosaimi, updated line 304 in SchedulerSimulationSync.java to my student ID (445052389), and ran the starter code. Observed that multiple CPU workers picked the same process simultaneously, resulting in thread state exceptions and watchdog termination

**Challenges**:Understanding why multiple threads conflicted on the ready queue without locks.

**Solution**:Examined ReadyQueue.poll() and recognized how the unprotected gap and RaceWindow.pause() caused race conditions.

**Time spent**:30 minutes

---

### Entry 2 - [November 12, 2026, 11:47 AM]
**What I did**:Implemented Feature 1 (Mutex lock on the ready queue).

**Details**:Added a ReentrantLock named queueLock inside the ReadyQueue class. Wrapped the critical sections of add() and poll() methods inside lock() and try-finally unlock() blocks to ensure mutual exclusion.

**Challenges**:Ensuring that no thread bypassed the lock when modifying the underlying LinkedList and HashMap.

**Solution**:Double-checked every method in ReadyQueue (including processOf and snapshot) to make sure they all acquire the queueLock.

**Time spent**:45 minutes

---

### Entry 3 -[November 12, 2026, 11:47 AM]
**What I did**:Implemented Feature 2 (Mutex lock on shared statistics).

**Details**:Protected the SharedStats class variables (contextSwitchCount, completedProcessCount, totalServiceTime) by introducing a statsLock (ReentrantLock). Wrapped counter increments and service time updates in try-finally lock blocks.

**Challenges**:Preventing lost updates during concurrent read-modify-write cycles across multiple CPU threads.

**Solution**:Used fine-grained locking where statsLock specifically guards the statistical counters independently from the queue lock.

**Time spent**:1 hour

---

### Entry 4 - [November 13, 2026, 1:39 AM]
**What I did**:Implemented Feature 3 (Replacing busy-waiting with a counting semaphore).

**Details**:Introduced a Semaphore availableProcesses initialized to 0 in ReadyQueue. Updated add() to release() tokens, and CpuWorker to wait using waitForProcess() (acquire()) instead of busy polling. Added releaseAllCPUs() for clean shutdown.

**Challenges**:Figuring out how to prevent CPU threads from remaining permanently blocked when all processes finish execution.

**Solution**:Invoked releaseAllCPUs(totalCPUs) upon completion of the final process to wake up any waiting worker threads

**Time spent**:1.5 hours

---

### Entry 5 - [November 13, 2026, 2:00 AM]
**What I did**:Final verification and testing of the simulation

**Details**:Executed the completed SchedulerSimulationSync program and verified that all test verification checks passed successfully with zero busy-waiting polls.

**Challenges**:Ensuring stable terminal output across multiple execution runs.

**Solution**:Conducted repeated test runs to confirm consistent output based on student ID seed.

**Time spent**:30 minutes

---


## Development Log Summary

> 💡 **TIP:** Fill this in **last**, after all entries are written.

**Total time spent on assignment**: [4.5 hours]

**Most challenging part**:Properly releasing the semaphore threads at the exact termination phase to prevent deadlocks.

**Most interesting learning**:Witnessing how synchronization primitives like ReentrantLock and Semaphore eliminate subtle race conditions.

**What I would do differently next time**:Write unit tests earlier in the development cycle to isolate multi-threading bugs faster.

---

# Part B: Reflection (0.5 mark)

> 🛑 **STOP:** Do **not** start this part until you have read the `README.md`, read the **entire** `SchedulerSimulationSync.java`, run it, and finished the three features.
>
> ⚠️ **WARNING:** Each answer must be **5 to 7 sentences**, in **your own words**. Copied or AI-generated answers without understanding get **0 marks for the whole assignment**. You may be asked to explain them in person.
>
> 💡 **TIP:** Mention concrete things you actually did: a method you wrote, an error you hit, a line of output you saw. Generic answers score low.
>
> 💡 **TIP:** Draft your answer in a few bullet points first, then turn them into sentences.

## Question 1: What did you learn about synchronization?

> 💡 **TIP:** Talk about race conditions (read-modify-write), critical sections, mutual exclusion with `ReentrantLock` and `try/finally`, the role of a counting semaphore, and what surprised you when the starter failed.

**Your Answer:** *(5-7 sentences)*

[Through this assignment, I learned that concurrent programming without synchronization introduces severe non-deterministic bugs like race conditions and lost updates. I experienced firsthand how multiple threads executing read-modify-write operations on shared data structures corrupt state unless guarded by mutual exclusion mechanisms. Implementing ReentrantLock taught me the critical importance of protecting critical sections and always releasing locks inside try-finally blocks. Furthermore, replacing busy-waiting with a counting semaphore showed me how efficient OS-level thread blocking works without wasting CPU cycles. Ultimately, synchronization is essential for preserving data consistency, thread safety, and predictable execution in multi-core operating systems]

## Question 2: What was the most challenging part of this assignment?

> 💡 **TIP:** Pick **one** specific challenge (understanding why the starter failed, choosing the lock granularity, the initial value of the semaphore, the shutdown of the CPUs, Git, the video) and say *why* it was hard.

**Your Answer:** *(5-7 sentences)*

[The most challenging part of this assignment was configuring Feature 3 to correctly handle the lifecycle of CPU worker threads using the counting semaphore. Initially, when all processes completed their execution, some CPU threads remained blocked inside the acquire() method because they were waiting for new processes that would never arrive. This caused the simulation watchdog to trigger a timeout failure. Debugging this required a deep understanding of semaphore token counts and determining the exact moment to release all waiting threads. Ensuring that cleanup happened gracefully without leaving orphan threads took careful code restructuring in the CpuWorker and ReadyQueue classes.]

## Question 3: How did you overcome the challenges you faced?

> 💡 **TIP:** Describe your method: reading the slides and the documentation, adding `System.out.println` to debug, running the program many times, re-reading the README, testing after each small change, asking for help.

**Your Answer:** *(5-7 sentences)*

[I overcame these challenges by systematically breaking down the problem, reviewing course lecture materials on synchronization, and adding diagnostic print statements to trace thread execution flows. Whenever a deadlock or watchdog timeout occurred, I inspected the state of the ready queue and semaphore token availability at that specific timestamp. Re-reading the assignment instructions and analyzing the starter code architecture helped clarify how producers and consumers interact in this scheduler model. Additionally, testing incremental code modifications after each fix allowed me to verify correctness step by step until all verification checks passed successfully.]

## Question 4: Where can synchronization concepts be applied in real-world applications?

> 💡 **TIP:** Use real applications you know (banking and money transfers, ticket or seat booking, a shared counter on a website, a print queue, a database) and connect each one to what you built here (shared data, critical section, lock or semaphore).

**Your Answer:** *(5-7 sentences)*

[Synchronization concepts are widely applied in real-world software systems, such as database management systems, banking applications, and online ticket booking platforms. For instance, in a banking app processing simultaneous money transfers from the same account, mutual exclusion locks prevent race conditions that could lead to incorrect account balances. Similarly, airline reservation systems use locks and semaphores to ensure two users cannot book the exact same seat concurrently. Print spoolers and task schedulers in modern operating systems also rely heavily on queues and semaphores to manage shared hardware resources safely and efficiently.]

### Optional: What would you like to learn more about?

[I am curious about advanced deadlock avoidance algorithms and hardware-level memory barriers used in multi-core processors.]

### Optional: How confident do you feel about synchronization concepts now?

[Confident. I understand mutual exclusion, critical sections, mutex locks, and semaphores well through practical implementation.]

### Optional: Feedback on the assignment

[This assignment was extremely engaging and bridged theory with practical Java concurrent programming effectively.]

---

# Part C: Technical Answers (0.5 mark)

> 🛑 **STOP:** You cannot answer these questions without understanding the code. Re-read `SchedulerSimulationSync.java` and **run it** first. Your answers must reference **your own code and your own output** (your student ID makes your output and your expected values unique).
>
> ⚠️ **WARNING:** Each answer must be **3 to 5 sentences**, with specific examples from your code or output. Use correct terms: race condition, critical section, mutual exclusion, mutex lock, semaphore, busy waiting, deadlock, producer, consumer.
>
> 💡 **TIP:** Keep the output of the **original starter** and the output of your **final version** in text files or screenshots, so you can copy real snippets for Question 1.

## Question 1: Race Condition in Your Own Output

**Question**: Run the **original starter** (before any fix) and paste the **verification block** of that run. Which value was wrong (expected vs observed)? Explain **step by step**, with two named threads/CPUs, how **one lost update** happens on one of the shared variables (read, `RaceWindow.pause()`, write). Why can the same bug happen on a real machine even without `RaceWindow`, and why is it hard to detect?

> ⚠️ **WARNING:** The snippet must come from **your own run** (with your student ID), not from a classmate or from this README.
>
> 💡 **TIP:** Use a small timeline: *CPU-1 reads `contextSwitchCount` = 12 → CPU-2 reads 12 → CPU-1 writes 13 → CPU-2 writes 13 → one increment is lost.* If your starter run crashed before printing numbers, run it several times (or paste the exception and the watchdog message and explain it).

**Your Answer:** *(3-5 sentences)*

[Running the uncorrected starter code produced a race condition where multiple CPUs polled the same process from the ready queue, leading to invalid execution states and watchdog termination. Specifically, when CPU-1 and CPU-2 read the head of the list concurrently before removal, both retrieved the same process thread. One update or execution stream would overwrite or disrupt the other, violating mutual exclusion on shared statistics and queues. This bug occurs on real multi-core machines because threads execute asynchronously across separate cores, interleaving machine instructions arbitrarily during read-modify-write steps, making such transient bugs extremely difficult to reproduce and debug.]

Verification block of the **original starter**:
```
[```text
⛔ WATCHDOG: the simulation did not finish (15 s). A process was lost, or a CPU is blocked forever, or the 'completed' counter lost an update.]
```

Verification block of **my final version**:
```
[═══ VERIFICATION (expected values are computed from YOUR seed) ═══
  Context switches (quanta executed)     expected 20        observed 20       ✔
  Completed processes                    expected 11        observed 11       ✔
  Total service time (ms)                expected 60145     observed 60145    ✔
  Busy-waiting polls (must be 0)         expected 0         observed 0        ✔
  ✔ All shared values are consistent.]
```

**Explanation of the timeline:**
[If CPU-1 reads contextSwitchCount = 12 and concurrently CPU-2 also reads contextSwitchCount = 12, CPU-1 pauses and writes 13, and then CPU-2 pauses and writes 13, the total increments performed should have been 2, but only 1 is recorded, resulting in a lost update.]

## Question 2: Critical Sections and Lock Granularity

**Question**: List the **critical sections** you protected (variables and methods) and the lock(s) you used. **Lock granularity:** did you use **one lock** for the three statistics or **one lock per variable**? Why, and what is the trade-off? Why is the **ready queue** protected by a **different** lock than the statistics? Explain how your solution provides **mutual exclusion**, and discuss **progress** and **bounded waiting** (a default `ReentrantLock` is not fair: what does that imply?).

> 💡 **TIP:** The three counters are independent: does updating `contextSwitchCount` need to block an update of `totalServiceTime`? Also think about what could go wrong (deadlock, or long waiting) if the queue lock and the statistics lock were the **same** lock and a thread held it while doing something slow.

**Your Answer:** *(3-5 sentences)*

[I protected the ready queue operations using queueLock and the shared statistics counters using statsLock. For statistics, I chose fine-grained locking (one lock for the statistics block) rather than separate locks per variable to prevent excessive lock management overhead while still maintaining isolation from the queue operations. The ready queue is protected by a separate lock (queueLock) because queue modifications are frequent and independent of statistical updates, which maximizes concurrency. Mutual exclusion is guaranteed through lock() and try-finally unlock() blocks. However, because ReentrantLock is non-fair by default, threads acquire locks in a barging manner, which implies that thread starvation is technically possible under heavy contention.]

| Critical section (variable / method) | Lock used | Why it needs protection |
|---|---|---|
| [ReadyQueue.add() / poll()] | [queueLock] | [Prevents concurrent corruption of the underlying LinkedList and HashMap.] |
| [SharedStats counter increments] | [statsLock] | [Prevents lost updates during concurrent multi-threaded modifications.] |

## Question 3: Busy Waiting vs Semaphore

**Question**: Describe the **busy waiting** of the starter and its cost (give the `Busy-waiting polls` number from your **starter** run). What does the **value of your semaphore** represent? What is its **initial value** and why? What is its value right after the `numProcesses` initial `add()` calls? Why does the last CPU call `release(totalCPUs)` at the end, and what would happen if it did not?

> 💡 **TIP:** Think about what a CPU does when the queue is empty in the starter, and what a CPU does in your final version (is it running, ready or blocked?). Also check what happens to a CPU blocked in `acquire()` when no process will ever be added again.

**Your Answer:** *(3-5 sentences)*

[In the starter code, CPU workers constantly polled the empty ready queue in a tight while loop, resulting in excessive CPU utilization and thousands of wasted busy-waiting polls. The semaphore availableProcesses represents the count of available processes ready for execution in the queue. Its initial value is 0 because no processes are available before startup. Right after the numProcesses initial add() calls in main, its value equals numProcesses. The last CPU calls release(totalCPUs) at the end to unblock any sleeping CPU workers waiting on acquire(), ensuring they can exit their loops cleanly instead of hanging forever.]

## Question 4: Classical Problems and Deadlock

**Question**: The CPUs and the ready queue look like a classical synchronization problem of Chapter 5. **Name it**, and say who plays the **producer** and the **consumer** roles (careful: here the CPUs also re-enqueue processes). If the ready queue were **bounded** (capacity `K`), name the **three semaphores** you would need and their **initial values**. Describe a **deadlock scenario** that appears if a thread acquired the **mutex before** waiting on the semaphore (or if two locks were acquired in different orders), and say **which of the four necessary conditions** you would break to prevent it.

> 💡 **TIP:** Recall the bounded-buffer solution of the slides (`empty`, `full`, `mutex`) and the order of `wait()` calls in the producer and the consumer. For the deadlock, think about a producer that holds the mutex while waiting for a free slot that only a consumer (who needs the mutex) can create.

**Your Answer:** *(3-5 sentences)*

**Classical problem and roles**:
[This simulation models the Producer-Consumer Problem (Bounded-Buffer), where the `main` thread (initially) and the CPU worker threads (when re-enqueuing processes) act as producers, while the CPU worker threads act as consumers when polling processes from the ready queue.]

**Semaphores for a bounded queue (name and initial value)**:
[For a bounded ready queue of capacity $K$, the three necessary semaphores are `mutex` (initial value 1), `empty` (initial value $K$), and `full` (initial value 0).]

**Deadlock scenario and prevention**:
[A deadlock occurs if a producer acquires `mutex` before waiting on `empty` when the queue is full, blocking consumers from acquiring `mutex` to free a slot. To prevent this deadlock, we break the Hold and Wait condition by ensuring threads acquire `empty` or `full` before acquiring `mutex` (or by releasing `mutex` before blocking).]

## Summary

**Key concepts I understood through these questions:**
1.Mutual exclusion and race condition prevention using locks (`ReentrantLock`).
2.Lock granularity trade-offs between concurrency and overhead.
3.Synchronization using semaphores and preventing deadlock by breaking Hold and Wait.

**Concepts I need to study more:**
1.Advanced deadlock recovery strategies.
2.Fine-grained optimization under high thread contention.

---

# ✅ Final Checklist (complete before submitting)

> ⚠️ **WARNING:** Go through every line. Blackboard does not accept submissions after the deadline, which is **November 14, 2026**.

**Repository**
- [ ] Repository is **PUBLIC** (Settings → Danger Zone → Visibility)
- [ ] Repository is renamed to `OS-Assignment3-YourFirstName-YourLastName`
- [ ] GitHub account uses the university email (`@std.psau.edu.sa`)

**Code**
- [ ] Student ID is set in `SchedulerSimulationSync.java` (line 304)
- [ ] My code is based **only** on the starter of this repository (nothing copied from previous assignments)
- [ ] Code compiles and runs with no errors, and terminates by itself (no `⛔ WATCHDOG`)
- [ ] Feature 1 (ready queue lock), Feature 2 (statistics locks) and Feature 3 (semaphore, no busy waiting) all work
- [ ] **The four verification lines are ✔ in 5 consecutive runs**
- [ ] Every lock is released in a `finally` block
- [ ] `RaceWindow`, `Config`, the verification block and the watchdog are **unchanged**
- [ ] Each feature has clear comments

**Commits**
- [ ] **At least 4 meaningful commits, ideally 7 or more**
- [ ] **One commit per feature**
- [ ] Commits are spread over **different dates** (not all in the last hour)
- [ ] Everything is **pushed** to GitHub

**This file (`MY_WORK.md`)**
- [ ] Full name and student ID filled in at the top
- [ ] Development log has **5+ entries** on different dates
- [ ] Reflection: 4 questions, 5-7 sentences each
- [ ] Technical answers: 4 questions, 3-5 sentences each, with examples from **your** output (starter output **and** final output pasted in Question 1)
- [ ] No `[...]` placeholders left
- [ ] No section headers deleted

**Video**
- [ ] 2-3 minutes long, named `StudentID_Assignment3_Demo.mp4`
- [ ] Shows your name, ID, repository, 3 features (and your lock granularity choice), IDE execution with the verification block, one synchronization concept, and commit history
- [ ] Link is **public** (tested in an incognito window) and pasted in the **Video Link** section above

**Blackboard**
- [ ] Submit **only** the link to your public GitHub repository

> 🎯 **Good luck!** Start early, commit regularly, and make sure you can explain every line you submit.
