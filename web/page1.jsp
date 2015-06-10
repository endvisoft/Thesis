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
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>    
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
	 $("#tablediv").hide();
         $("#loading").hide();
         $('#form1').submit(function(e) {
         $("#loading").show();
            e.preventDefault(); // prevent default form submit
            var kl = $("#kalimat").val();
            var dataString = 'kalimat='+ kl;
            $.ajax({
              url: 'ControlServlet', // form action url
              type: 'POST', // form submit method get/post
              dataType: 'html', // request type html/json/xml
              data: dataString,
              success: function(data) {
                  $("#loading").hide();
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
                top: 40%;
                left: 40%;
                width:50em;
                min-height: 18em;
                margin-top: -9em; /*set to a negative number 1/2 of your height*/
                margin-left: -15em; /*set to a negative number 1/2 of your width*/
                border: 1px solid #ccc;
                background-color: #f3f3f3;
                 border-radius: 15px;
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
                float:left;
                position: relative;
                margin-top:140px; margin-left: 10%;
                padding-top: 20px;
                width: 40em;
                height: 100%;
            }
            .form-control
            {
                width: 40em;
            }
            .load{
                    width: 40%;
                    float:left;
                    position: relative;
                    margin-top:140px; 
                    margin-left: 30%;
                    padding-top: 20px; 
                    padding-bottom: 20px; 
                    text-align: center;
                    color: #fff;
                    font-family: 'Raleway', sans-serif;
                    font-size: 18px;
                    font-weight: 600;
            }
            .btn{ margin-left: 15%; }
        </style>
        <div class="mydiv">
            <div class="formdiv">
            <form action="" method="get" id="form1">
                <!--input type="text" name="kalimat" id="kalimat" style="width:300px; "-->
                <textarea class="form-control" maxlength="5000" name="kalimat" id="kalimat"></textarea><br/>
                <input type="submit" class="btn btn-primary btn-lg btn-block" id="submitkata" value="Submit">
            </form>
            </div>
            <br/>
            <div id="loading" class="load">
            <img id="loader-img" alt="" src="http://buzzly.fr/design/progression1.gif" width="50" height="50" align="center" />
            </div>
            <div id="tablediv" class="divtable">
                <h4>Hasil Pencarian </h4>
                <table class="table table-striped" cellspacing="5" id="resulttable">
                <col width="80">
                <col width="300">
                <tr style="text-align:left;">
	        <th>Kata</th>
	        <th>Sense</th>
	    </tr>
            </table>
            </div>
        </div>
    </body>
</html>
