import '../Styling/Home.css'; 

export default function About() {
    return (
        <div className="home-container">
            <h1>About the Ethics Application Portal</h1>
            
            <section className="about-section">
                <h2>For Applicants</h2>
                <p>This section provides applicants with all necessary tools and information needed for submitting their ethics applications.</p>
                <dl>
                    <dt>Profile</dt>
                    <dd>From the Profile page a user is able to view the details assocaited with their account and also the submission(s) that they have made</dd>
                    <dt>Submission</dt>
                    <dd>
                        <dl>
                            <dd>Submit your initial ethics application and supporting documents.</dd>
                            <dt>Automated Review</dt>
                            <dd>When you review your Ethics Application, the information in the PDF is parsed and stored in a custom Data Strcutre. A series 
                                of checks are then made on the application to help prevent common errors made in applications. This data is then stored to populate charts on 
                                the Analytics page available to faculty members.
                            </dd>
                        </dl>
                    </dd>
                </dl>
            </section>
            
            <section className="about-section">
                <h2>For Faculty</h2>
                <p>Faculty members can oversee and manage the application process through the following pages:</p>
                <ul>
                    <li>Submission Dashboard - Quick access to view all submissions made by applicants. A number of filters are available to refine the results shown
                        and it is from this page that you are able to track submissions that are relevant to you.
                    </li>
                    <li>Submission Tracker - This is where you are able to organise your tracked submissions into folders, download the files associated with a submission and update the
                        status of an application.</li>
                    <li>Analytics - This dashboard shows trends in research based on the information parsed from ethics applications.</li>
                </ul>
            </section>
            
            <section className="about-section">
                <h2>Implementation of the EASMS</h2>
                <p>The Ethics Application Support Management System (EASMS) is designed to streamline and enhance the ethics application process.</p>
                <p>The Client of this applicant was developed using JavaScript and React, while the Server was developed with Java and Spring.</p>
                <p>A MySQL database is used to store most of the information used in this application, however an Amazon AWS S3 bucked is used to store the actual files uploaded by users</p>
            </section>
        </div>
    );
}