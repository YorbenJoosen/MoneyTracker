# 1. General overview

# 2. Components
## 2.1. Input | 

## 2.2. Ticket Calculation |

## 2.3. Database | Singleton | Abstract Factory Pattern
Generic interface for selecting and inserting tickets and users.
Database is currently implemented as an ArrayList but could be anything.

## 2.4. Ticket Factory | Factory ?
**Methods**
* create_equal_all(owner, total_price)
* create_equal_list(owner, total_price, person[])
* create_individual_list(owner, {person, price}[])

# 3. Classes
## 3.1. Person
string identifier

## 3.2. Ticket
* Owner: Person
* Transactions
* 