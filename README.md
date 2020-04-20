# cse237-project: Virtual Stock Market
Members: Aaron Arora, Tori Dakich, Jeremy Kunen

User stories completed this iteration (iteration 2):

- Real names are given for equities and stored in equities.txt. User can enter and remove names.
- Real names are given for clients and stored in clients.txt. User can enter and remove names.
- Merged stock market main execution and input codes with user interface.
- Improved user interface aesthetics.
- User can generate random input given price limit and quantity limit. Input gets stored to a file.
- When main stock market algorithhm is executed, verbose output is written to output file with same name as input file (except .out instead of .in).

User stories for next iteration:
- Error checking/exceptions.
- Dialog boxes for all actions performed (success or failure).

What is implemented but doesn't currently work:
- User interface tends to crash when false/erroneous input is specified. Plan on implementing error checking/exceptions to deal with this.
- Median and time travlelers algorithms implemented but not yet runnable from the UI.

***IMPORTANT NOTE***
When specifying the input file name "filename", the file is stored in input/filename.in.
Corresponding output file is in output/filename.out
The user interface will not display output, you need to navigate to files manually (file can be too long to display).

HOW TO RUN:
- Run the script with ./script.sh
- "Generate random input" will add input file to input/ directory (if filename given is x, then input/x.in will be written)
- Generate output by pressing "Execute" (will write to outpu/x.out)
- Can re-run existing input file with "Input file" (make sure that if x is typed in, input/x.in exists)

Feedback from iteration 1:
- Removed .idea from .gitignore
- Added missing asserts to tests
- Assigning team members to user stories on Kanban
- Deleted testing branch
- In response to code being "hard to follow" the code is rather complex and while comments can help, the user needs to spend larger amount of time than usual understanding the full mechanisms. Not much can be done about that, code had to be structured in this fashion for the algorithm to have all the features we wanted.
- In response to "branches missing/incomplete" we have tried to organize branches to the best of our abilities. Each branch is mutually exclusive and independent of each other (which was the expectation).
- In response to "commented out code in main" the main class is irrelevant for this iteration. Should only focus on GUI related code this time.

Iteration 1

- User can request to buy a stock.
- User can request to sell a stock.
- User can set a price for the order.
- A seller matches the best buy price.
- A buyer matches the best sell price.
- Basic UI completed.
- Transaction tests completed.
- Median tests completed.
- Time traveler tests completed.
- Verbose console output.

What commands are needed to compile and run your code from the command line?
- We are including a script that will run the code and display the user interface.
- You cannot use eclipse because our project was developed in intellij.

Summary:
Our project emulates the stock market. It’s a virtual stock market. We are looking at the transactions that occur in one day. They are in the form of a purchase or sale. Basically, the program itself isn’t just using the stock market. You are really a broker and need to find the best match between buyer and seller of a given stock.

Target audience:
We are aiming for mostly young people because they are really keen on investing. They’re the ones who are more curious about how these transactions work.

Key features: 
Given requests to buy or sell stocks, the program will display the outputs of the transaction. We plan to display this in a User Interface. The program allows users to understand how trades work. You’re inputting certain requests and outputting only a few. With the program, you can actually see the mechanism behind it. The user will have the chance to generate transaction requests from the stock market and the requests are generated randomly. This feature is realistic because in reality one can’t predict what requests come in a day. The randomness makes it realistic. Error checking will make sure all the input is properly formatted. If there is a problem with the format, it will raise an exception. 
Algorithm details: The program will read a sequential list of sell or buy requests upon recieving each request. The program will attempt to match that request with another request that happened earlier. If this request is a buy, it will find a prevous sell request of the same equity. If this request is a sell, it will find a previous buy request of the same equity.

 The constraints are:
(1) if the current request is a buy then the previous matched sell request must have a value that is no greater than the current buy request.
(2) if the current request is a sell then the previous matched buy request must have a value that is no less than the current sell request.

If multiple potential matches exist, then it will pick the best value.
If there is a tie then it will pick the match that occured the earliest.

Median output: when median output is requested, the program will generate the median matched prices for each equity at each time stamp. Then it will plot the median values as a function of the timestamp. If there is an even number of prices, then it will output the average of the middle two.

Time traveler output: when time traveler output is requested, the program will find the ideal times to buy and sell stocks of each equity such that as much profit is generated as possible.

