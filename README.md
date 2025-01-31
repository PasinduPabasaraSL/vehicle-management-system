<h1>Vehicle Management System</h1>

<p>A simple <strong>JavaFX</strong> application to manage vehicle details with <strong>MySQL</strong> integration.</p>

<h2>Features</h2>
<ul>
  <li>Add, view, delete and update vehicle details (Make, Model, Registration Number, Year).</li>
  <li>purchase order</li>
</ul>

<h2>Requirements</h2>
<ul>
  <li><strong>Java JDK 8+</strong></li>
  <li><strong>MySQL</strong> database</li>
  <li><strong>MySQL JDBC Driver</strong></li>
</ul>

<h2>Setup</h2>
<ol>
  <li>Create the database and table in MySQL:</li>
</ol>
<pre><code>CREATE DATABASE vehicle_management;

USE vehicle_management;

CREATE TABLE vehicles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    make VARCHAR(50),
    model VARCHAR(50),
    registration_number VARCHAR(50),
    year INT
);
</code></pre>

<ol start="2">
  <li>Update <code>DatabaseConfig.java</code> with your MySQL username, password, and database URL.</li>
  <li>Add the MySQL JDBC driver to your project.</li>
  <li>Run the application.</li>
</ol>

<h2>Usage</h2>
<ul>
  <li><strong>Add Vehicle</strong>: Enter details and click "Add Vehicle."</li>
  <li><strong>View Vehicles</strong>: The table shows all vehicles.</li>
  <li><strong>Update Vehicle</strong>: Select a vehicle, edit details, and click "Update Vehicle."</li>
  <li><strong>Delete Vehicle</strong>: Wnter vehicle id and "delete vehicle"</li>
  <li><strong>Purchase Order</strong>: purchase a order</li>
</ul>

<h2>License</h2>
<p>MIT License. See <a href="LICENSE">LICENSE</a> for details.</p>
