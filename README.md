# Job Reporting Management System SERVER

Technologies: 
Java 1.7 (Multithreading, Concurrency Control, Execution Framework), REST, Jersey, Google Maps API, Apache Tomcat server, Eclipse, Maven, JProfiler, Git


There are several small to medium agencies or companies in Spanish area which deals in consumer services business. Few instances of the services provided by these companies are: Swimming Pool Maintenances, Garden Lawn Cleaning, House maintenances, Plumber work, domestic helps (on hourly basis), cabs facility, travel assistances, luggage transportation and various other kinds of services. In all these services, consumer is directly involved to convey the needs of the service and direct the service the way it require.

On need of any kind of service, consumer directly call one of these agency/company (which provides the relevant service) and that agency assign the person or team of people to fulfil the consumer’s service. The assigned person or team visit the service’ location, performs the job, takes the feedback from the client and submit the report to the agency. This is the complete job cycle to complete any consumer service, however there can be several other tasks can also be involved in between.

All these operations involved in providing consumer service were being managed manually where the assigned resource was creating the paper based report, filling up the paper-form, getting the customer feedback and then manually submitting the paper report to the agency. This kind of manual handing was not at all efficient and consuming lots of efforts in terms of resources time and engagement to complete one job. Also, it was not directly apparent or trustworthy way to know the resource location who is assigned to perform the job, what time the resource went to location and when the job was finished.

What was needed is a complete package of online and automated system which can override all these manual operations of consumer job handing and should function based on the user type.

Major Functional areas :

1.	System with the features based on various user types and relevance
2.	Single system supporting diversified nature of businesses of the various agencies/companies and providing uniqueness with adjoining only those functionalities which are needed by the respective agency/company
3.	Automated Reporting Management with online form which also include consumer feedback
4.	Remote monitoring of resource’s location with the timings intervals
5.	Onsite report submission with the support of real time images, signatures and other details
6.	Customizable interface to support the modifications of the report forms as per need without any IT person intervention
7.	Interface at agency/company level to view all the reports submitted by the resources 
8.	Email Notifications
9.	Functionality in the application to view the live locations of the resources working in the consumer location in the forms of maps
10.	Internationalization support


Major Challenges :

1. Finding a way to manage various agencies/companies of very diversified natures of businesses and bringing all of them in one umbrella while keeping their uniqueness intact and not mixing up one agency/company requirement into another. For instance, ‘Organization A’ deals in services and ‘Organization B’ deals in construction. As the nature of these organizations are different so the application usages. ‘Organization A’ would need different functionalities and ‘Organization B’ would need different functionalities.
2. Onsite report submission as it was not possible that the resource is always connected to internet and hence, in this case it was require that solution should be capable to handle this scenario
