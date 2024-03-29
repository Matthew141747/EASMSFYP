import '../Styling/Home.css'; 

export default function Home() {
    return (
        <div className="home-container">
            <h1>Welcome to the Ethics Application Portal</h1>
            
            <section className="ethics-process">
                <h2>Ethics Application Requirements</h2>
                <p>Please note that an Ethics Application must contain the following:</p>
                <ol>
                    <li>
                        <strong>Application form:</strong> The majority of applications will use the Expedited Ethics form. The Full Ethics Form is only in the case where underage and/or vulnerable participants are involved. See video "How to fill out an application (Sections 1-4)" for more information.
                    </li>
                    <li>
                        <strong>Relevant documents:</strong> Please find these forms within the Application form that the participants will view, i.e., Participation Information Sheet(s) and Consent form(s). These need to be written with the participant in mind, avoiding complex jargon and ensuring that grammar is checked. If there are separate parts to the study (survey, followed by a focus group), then separate Information Sheets and Consent Forms need to be provided for each phase.
                    </li>
                    {/* Add other points similarly */}
                </ol>
            </section>
            
            <section className="application-forms">
                <h2>Application Forms</h2>
                <ul>
                    <li>Expedited Ethics Form - most studies will use this form</li>
                    <li>MAC Compatible Expedited Ethics Form</li>
                    <li>Full Ethics Form - for studies that include under 18s and/or vulnerable participants (see form and video for definitions)</li>
                    {/* Add other forms similarly */}
                </ul>
                <p>Further guidance and all forms are available for download.</p>
                {/* Buttons to download forms will go here */}
            </section>
            
            <section className="register-info">
                <h2>Getting Started</h2>
                <p>In order to use the Ethics Application Portal, you must register an account.</p>
                <button onClick={() => window.location.href='/Registration'}>Register Now</button>
            </section>
            
            {/* Add more sections as needed */}
        </div>
    );
}