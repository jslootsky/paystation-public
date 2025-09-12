# PayStation Main Design Document

**CIS 3296 Section 0x**

**Fall 2024**

**Team Members**

<table>
<tr>
    <td align="center">
        <a href="https://github.com/ApplebaumIan">
            <img src="https://avatars.githubusercontent.com/u/9451941?v=4" width="100;" alt="ApplebaumIan"/>
            <br />
            <sub><b>Ian Tyler Applebaum</b></sub>
        </a>
    </td>
    <td align="center">
        <a href="https://github.com/thanhnguyen46">
            <img src="https://avatars.githubusercontent.com/u/60533187?v=4" width="100;" alt="Thanh"/>
            <br />
            <sub><b>Thanh Nguyen</b></sub>
        </a>
    </td>
</tr>
</table>

## Document Overview
This Design Document describes the software architecture and how the requirements are mapped into the design. This document will be a combination of diagrams and text that is describing what the diagrams are showing. The Design Document also specify the complete design of the software implementation using Javadoc.

## Architecture
This section describes the different components and their interfaces using UML. For example: client, server, database. For each component provide class diagrams showing the classes to be developed (or used) and their relationship.

```mermaid
%% Define all Classes and Interfaces
classDiagram
class PayStation
<< interface >> PayStation
class PayStationImpl
class ReceiptImpl
class Receipt
<< interface >> Receipt
class IllegalCoinException
class Exception
<< interface >> Exception

%% Provide Attributes and Methods
class PayStation {
    + addPayment(int)
    + readDisplay() int
    + buy() Receipt
    %% Spaces are needed between < > in mermaid for some reason.       👇   👇
    + cancel() Map< int, int > 
    + empty() int
}

class PayStationImpl {
    - insertedSoFar: int
    - timeBought: int
    - coinMap: Map< Integer, Integer >
    - totalMoney: int
    + reset()
}

class Receipt {
    + value() int
}

class ReceiptImpl {
    - value: int
}

class IllegalCoinException {
    - value: int
}

%% Define relationships

PayStationImpl ..|> PayStation
%% Multiplicity only seems to render in Chrome based browsers as of 09/12/24. Graders should make sure to view Mermaid there.  
PayStationImpl "1"-->"1" ReceiptImpl
PayStationImpl ..> IllegalCoinException
ReceiptImpl ..|> Receipt
IllegalCoinException ..|> Exception
```

## Detailed Design API
For each class define the data fields, methods.
-	The purpose of the class.
-	The purpose of each data field.
-	The purpose of each method
-	Pre-conditions if any.
-	Post-conditions if any.
-	Parameters and data types
-	Return value and output variables
-	Exceptions thrown*.
This information should be in structured comments (e.g. Javadoc) in the source files. A documentation generation tool (e.g. Javadoc) may be used to generate the document as a draft.


{% include footer-scripts.html mermaid=true %}
