<%-- 
    Document   : page1
    Created on : Apr 3, 2015, 1:37:55 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
	 $("#tablediv").hide();
         $('#form1').submit(function(e) {
            e.preventDefault(); // prevent default form submit
            var kl = $("#kalimat").val();
            var dataString = 'kalimat='+ kl;
            $.ajax({
              url: 'ControlServlet', // form action url
              type: 'POST', // form submit method get/post
              dataType: 'html', // request type html/json/xml
              data: dataString,
              success: function(data) {
                  
                  var table = document.getElementById("resulttable");
                    //or use :  var table = document.all.tableid;
                    for(var i = table.rows.length - 1; i > 0; i--)
                    {
                        table.deleteRow(i);
                    }
                  
                  var content = '';
                    var json = jQuery.parseJSON(data);
                    
                    $.each(json, function(i, post) {
                        content += "<tr><td>"+post.kata+"</td><td>"+post.sense+"</td></tr>";
                    });
                    $("#resulttable").append(content);
                    $("#tablediv").show();  
              }
            });
            
          });
         
	});
	</script>    
        <style type="text/css">
            .mydiv {
                position:absolute;
                top: 50%;
                left: 50%;
                width:30em;
                height:18em;
                margin-top: -9em; /*set to a negative number 1/2 of your height*/
                margin-left: -15em; /*set to a negative number 1/2 of your width*/
                border: 1px solid #ccc;
                background-color: #f3f3f3;
            } 
            .formdiv
            {
                position:absolute;
                margin-top: 20px; 
                margin-left: 10%;
                width:30em; 
                height: 2em; 
            }
            .divtable
            {
                float:left;margin-top:50px; margin-left:20%; padding:15px; 
                width: 20em;
                height: 100%;
            }
        </style>
        <div class="mydiv">
            <div class="formdiv">
            <form action="" method="get" id="form1">
                <input type="text" name="kalimat" id="kalimat" style="width:300px; ">
            
            <input type="submit" id="submitkata" value="Submit">
            </form>
            </div>
            <div id="tablediv" class="divtable">
            <table cellspacing="5" id="resulttable">
                <tr style="text-align:left;">
	        <th scope="col">Kata</th>
	        <th scope="col">Sense</th>
	    </tr>
            </table>
            </div>
        </div>
    </body>
</html>
