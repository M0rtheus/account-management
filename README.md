## Built With
  
* JAVA
* Spring Boot
* JPA
* H2 Database
* Apache Commons CSV
* Lombok
  
## Running the application
- Clone the Git repository
- Import the the app to IDE as a maven project
- Choose the Spring Boot Application file
- Run as Java Application
  
## Endpoints
  
### "/insertStatementCSV"  
&nbsp;&nbsp;To insert the statements you need to provide a valid CSV file with statements in HTTP request body with KEY - "file".  
&nbsp;&nbsp;The CSV file then will be parsed through Apache Commons CSV Library and saved to DB through JPA.  
&nbsp;&nbsp;For simplicity the H2 database was chosen but it can be switched to other.  
&nbsp;&nbsp;When parsing the CSV file the mandatory parameters will be validated through Spring validation with @NotNull annotation.  Empty string will be considered ass null values.
&nbsp;&nbsp;If any value in CSV file is null the RuntimeException will be thrown with explanation.  
&nbsp;&nbsp;Saved statements can be viewed in H2 console "http://localhost:8080/h2-console/"  
&nbsp;&nbsp;If importing was successful "200 OK" status will be returned, if the type was not CSV "406 Not Acceptable" status will be returned.  
&nbsp;&nbsp;Endpoint URL example: http://localhost:8080/insertStatementCSV  
&nbsp;&nbsp;The CSV file should have a following format:  [statement-valid.csv](https://github.com/M0rtheus/account-management/files/10611540/statement-valid.csv)  
>Account number,Operation date/time,Beneficiary,Comment,Amount,Currency  
>LT111000011111000001,2022-01-21T10:15:30,First Person,Notes 1,100.11,EUR  
>LT111000011111000002,2022-01-01T10:15:30,Second Person,Notes 2,200.21,EUR  
>LT111000011111000003,2022-03-10T10:15:30,Third Person,Notes 3,300.31,EUR  
>LT111000011111000001,2022-03-21T10:15:30,First Person,Notes 5,500.51,EUR  
>LT111000011111000001,2022-04-14T10:15:30,First Person,Notes 6,-600.60,EUR  
>LT111000011111000004,2022-02-28T10:15:30,Fourth Person,,400.41,EUR  
  
&nbsp;&nbsp;Successful HTTP POST request example in Postman:  
![image](https://user-images.githubusercontent.com/97054828/216830688-143c9723-627c-4ce3-b97d-eb90955df79d.png)

### "/getStatementCSV"  
&nbsp;&nbsp;When requesting this endpoint saved statements in DB will be validated, parsed to CSV format and returned with "200 OK" status.  
&nbsp;&nbsp;"Date from" and "Date to" can be passed as an optional parameters to filter the statements.  
&nbsp;&nbsp;URL example: "http://localhost:8080/getStatementCSV?dateFrom=2022-02-01&dateTo=2022-03-30"  
  
### "/getAccountBalance"  
&nbsp;&nbsp;To get the account balance you need to provide the "Account number" (mandatory) and "Date from" (optional) and "Date to" (optional) parameters.  
&nbsp;&nbsp;The statements corresponding to the parameters will be selected from DB then the balance will be calculated and returned with "200 OK" status.  
&nbsp;&nbsp;Otherwise if there were no corresponding statements the "204 No Content" status will be returned.  
&nbsp;&nbsp;URL example: "http://localhost:8080/getAccountBalance?accountNumber=LT111000011111000001&dateFrom=2022-01-22&dateTo=2022-04-13"  
<p align="right">(<a href="#top">back to top</a>)</p>
