<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" version="2.0">
    <jsp:directive.page language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
    <jsp:text>
        <![CDATA[ <!DOCTYPE html> ]]>
    </jsp:text>

<html lang="de">
  <head>
    <meta charset="utf-8" />
    <title>KIT Authenticator Tester</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="" />
    <meta name="author" content="" />

    <!-- Le styles -->
    <link href="/AuthTester/css/bootstrap.css" rel="stylesheet" />
    <link href="/AuthTester/css/main.css" rel="stylesheet" />
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link href="css/bootstrap-responsive.css" rel="stylesheet" />

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
  </head>

  <body class="devices">
    <f:view>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="/AuthTester">KIT Authenticator Tester</a>
          <div class="nav-collapse">
            <ul class="nav">
              <li><a href="/AuthTester/monitor">Arbeitsplatz-Monitor</a></li>
              <li class="active"><a href="/AuthTester/devices">Mobilgeräte verwalten</a></li>
              <li><a href="/AuthTester/info.html">Info</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">
		<div class="page-header">
		    <h1>Mobilgeräte verwalten <small>Mobiltelefone hinzufügen oder entfernen</small></h1>
		</div>

<p class="alert alert-info">
    <strong>Hinweis:</strong> Diese Seite ist selbstverständlich im Produktiveinsatz nur nach vorherigem Login sicht- und benutzbar.
</p>

        <div class="row">
        
	        <div id="devices" class="span8">
	           <h2>Registrierte Geräte</h2>
	           <p>Folgende Geräte sind derzeit mit Ihrem Account verknüpft:</p>

				<table class="table table-striped">
				<thead>
					<tr>
						<th>Bezeichnung</th>
						<th>Status</th>
						<th>Aktionen</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Wildfire S</td>
						<td><span class="label label-success">Aktiv</span></td>
						<td>
						    <div class="btn-group">
						        <a class="btn btn-info" href="#stats">Statistiken</a>
							    <a class="btn btn-warning" href="#deactivate">Deaktivieren</a>
							    <a class="btn btn-danger" href="#remove">Entfernen</a>
							</div>
						</td>
					</tr>

                    <tr>
                        <td>iPhone 4S</td>
                        <td><span class="label label-success">Aktiv</span></td>
                        <td>
                            <div class="btn-group">
                                <a class="btn btn-info" href="#stats">Statistiken</a>
                                <a class="btn btn-warning" href="#deactivate">Deaktivieren</a>
                                <a class="btn btn-danger" href="#remove">Entfernen</a>
                            </div>
                        </td>
                    </tr>
                    
                    <tr>
                        <td>Nexus One</td>
                        <td><span class="label label-warning">Inaktiv</span></td>
                        <td>
                            <div class="btn-group">
                                <a class="btn btn-info" href="#stats">Statistiken</a>
                                <a class="btn btn-success" href="#deactivate">Aktivieren</a>
                                <a class="btn btn-danger" href="#remove">Entfernen</a>
                            </div>
                        </td>
                    </tr>
				</tbody>
				</table>

	        </div>
	        
	        <div class="span4">
	           <div id="newDevice">
		           <h2>Neues Gerät</h2>
		           <p>Starten Sie die KIT Authenticator App auf Ihrem Smartphone und scannen Sie folgenden QR-Code ein, um ein neues Mobilgerät hinzuzufügen:</p>
		           <p id="qrcode" class="thumbnail"><img src="/AuthTester/img/qrcodes/${qrCodeFile.name}" width="300" height="300" alt="" /></p>
                </div>
	        </div>
	        
        </div>

    </div>

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="/AuthTester/js/jquery.js"></script>
    <script src="/AuthTester/js/bootstrap.js"></script>

  </f:view>
  </body>
</html>

</jsp:root>
