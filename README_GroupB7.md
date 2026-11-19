****Overview: What We Are Building**
A JavaFX desktop program that simulates the queues at the Akonor cafeteria. There are three counters (Meals, Drinks, Pastries). Each has its own first-in first-out queue and its own speed. Students place orders, wait in line, get served, and the program reports how long they waited.

***What the user does**
1. Types their name and the 8 digits of their student ID (the screen adds STU- ).
2. Ticks the items they want from the 12-item menu and chooses a quantity (1 to 10) for each.
3. Clicks Submit Order, then Start Simulation to watch the queues move. Stop
Simulation pauses and shows a summary.

**Rules the program follows**
A student joins one counter queue at a time and cannot place a second order until the first is finished.
Items from the same category are collected in one visit. Counters are visited in the order the categories were first ticked (Meat Pie then Jollof Rice means Pastries, then Meals).
Each counter serves one student at a time, front of the queue first. 
Service time = minutes per item × number of items (Meals 5, Pastries 2, Drinks 1).
Wait time = minutes from joining a counter's queue until service starts there.


**Expected output**
*On the screen*: a Place Order panel (name, student ID, items), a Queue Status panel with an "in queue" number for each counter, a Statistics panel (Total Students Served and Average Wait Time), and a control bar with the Start and Stop buttons and a status message. The numbers update every second while the simulation runs. One second on screen is one simulated minute.

*Warnings*: a pop-up with a clear message for an empty name, a bad student ID, no items ticked, or a student who already has an order waiting.

*On Stop*: a summary pop-up.

***Example**
Kofi ( STU-12345678 ) ticks a Meat Pie first, then Jollof Rice, and nobody else
orders. After Start: the Pastries queue shows 1 and he is served in 2 minutes; then the Meals queue shows 1 and he is served in 5 minutes. Total Students Served becomes 1, and when Stop is clicked the summary looks like this:
Simulated time: 7 min
Students served: 1
Meals: average wait 0.0 min, 0 in queue
Drinks: average wait 0.0 min, 0 in queue
Pastries: average wait 0.0 min, 0 in queue
Overall average wait: 0.0 min
If two students each order one Water before Start, the first waits 0 minutes and the second waits 1 minute, so the average wait shows 0.5 min .
