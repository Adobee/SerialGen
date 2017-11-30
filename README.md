# SerialGen
Serial number generator for Java 


###Usage

````
 // Create a builder
 SerialNumberBuilder builder = new SerialNumberBuilder();
 // Add serial part as u want 
 builder.addStaticPart("RUI").addDatePart("yyyyMMdd").addStreamPart(3, StreamFragment.OverflowType.RESET);
 // Build serial number instance 
 SerialNumber serialNumber = builder.build();
 // Get a serial number value
 String ret = serialNumber.getValue();
````
