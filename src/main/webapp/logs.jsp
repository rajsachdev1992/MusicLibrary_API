<%-- 
    Document   : logs
    Created on : 16 Apr, 2017, 1:07:15 AM
    Author     : Raj.Sachdev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style>
            table, th, td {
                border: 1px solid black;
                border-collapse: collapse;
            }
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logs and Analysis</title>
    </head>
    <body>
        <p>Top 10 Searched terms:</p>
        <%= request.getAttribute("top10List") %><br>
        <p>Average daily number of requests:</p>
        <%= request.getAttribute("averageDailyRequests") %><br>
        <p>Requests during different times of the day:</p>
        <%= request.getAttribute("quarterPercentages") %><br><br>
        <p>Log Entries:</p>
        <%= request.getAttribute("logList") %>
        
    </body>
</html>
