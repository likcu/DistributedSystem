package cs6650.proj3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;

public class MysqlTest implements RequestHandler<RequestDetails, ResponseDetails> {

  public ResponseDetails handleRequest(RequestDetails requestDetails,Context arg1){

    ResponseDetails responseDetails = new ResponseDetails();
    try {
      insertDetails(requestDetails,responseDetails);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return responseDetails;
  }

  private void insertDetails(RequestDetails requestDetails,ResponseDetails responseDetails) throws SQLException {
    Connection connection = getConnection();
    Statement statement = connection.createStatement();
    String query = getquery(requestDetails);
    int responseCode = statement.executeUpdate(query);
    if(1 == responseCode){
      responseDetails.setMessageId(responseCode);
      responseDetails.setMessageReason("successfully updated details");
    }
  }
  private String getquery(RequestDetails requestDetails){
    String query = "INSERT INTO likcu.employee(empid,empname) VALUES (";
    if(requestDetails != null){
      query = query.concat("'"+requestDetails.getUserId()+","+requestDetails.getUserName()+"')");
    }
    System.out.println("the query is "+ query);
    return query;
  }


  private Connection getConnection() throws SQLException {
    String url = "dbinstance.crw7lbh32rp9.us-west-1.rds.amazonaws.com";
    String username = "likcu";
    String password = "19930716";
    Connection conn = DriverManager.getConnection(url,username,password);
    return conn;
  }
}
