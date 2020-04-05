# cse237-project
Our project emulates the stock market. It’s a virtual stock market. We are looking at the transactions that occur in one day. They are in the form of a purchase or sale. Basically, the program itself isn’t just using the stock market. You are really a broker and need to find the best match between buyer and seller of a given stock.

Key features: Given requests to buy or sell stocks, the program will display the outputs of the transaction. We plan to display this in a User Interface. The program allows users to understand how trades work. You’re inputting certain requests and outputting only a few. With the program, you can actually see the mechanism behind it. The user will have the chance to generate transaction requests from the stock market and the requests are generated randomly. This feature is realistic because in reality one can’t predict what requests come in a day. The randomness makes it realistic. Error checking will make sure all the input is properly formatted. If there is a problem with the format, it will raise an exception. 
Algorithm details: The program will read a sequential list of sell or buy requests upon recieving each request. The program will attempt to match that request with another request that happened earlier. If this request is a buy, it will find a prevous sell request of the same equity. If this request is a sell, it will find a previous buy request of the same equity.

 The constraints are:
(1) if the current request is a buy then the previous matched sell request must have a value that is no greater than the current buy request.
(2) if the current request is a sell then the previous matched buy request must have a value that is no less than the current sell request.

If multiple potential matches exist, then it will pick the best value.
If there is a tie then it will pick the match that occured the earliest.

Median output: when median output is requested, the program will generate the median matched prices for each equity at each time stamp. Then it will plot the median values as a function of the timestamp. If there is an even number of prices, then it will output the average of the middle two.

Time traveler output: when time traveler output is requested, the program will find the ideal times to buy and sell stocks of each equity such that as much profit is generated as possible.
