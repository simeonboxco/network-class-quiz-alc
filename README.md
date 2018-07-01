# network-class-quiz-alc
An App to administer quiz for a networking class.

The main logic of the app is a multidimensional array that holds different elements of the question, these include the question itself, the type of question, the correct answer, options and also the response of the student.

Three diffent types of view handle the presentation of the options to the user depending on question type, the views are EditText, Radiogroup/Radiobutton and Checkboxes. One view is diplayed at a time, others are hidden away
based on the type of question to be displayed.

Below is the array logic in the array logic for the App.
    private String questions[][][] = {
            {
                    {"Select two addresses that are not assignable to hosts"},   //question
                    {"check"},      // Question type: available types ['radio','check','edit']
                    {"Broadcast Address", "Network Address", "", ""}, // answer
                    {"Broadcast Address", "Default gateway", "Network Address", "Host Address"}, // options
                    {"", "", "", "",} // responses
            },
            {
                    {"At what layer of the OSI model does routing occur?"},   //question
                    {"radio"},      // Question type: available types ['radio','check','edit']
                    {"Network", "", "", ""}, // answer
                    {"Network", "Transport", "Session", "Physical",}, // options
                    {"", "", "", "",} // responses
            },
            {
                    {"Which of these addresses is not within 192.168.0.0/25 subnet?"},   //question
                    {"radio"},      // Question type: available types ['radio','check','edit']
                    {"192.168.0.179", "", "", ""}, // answer
                    {"192.168.0.1", "192.168.0.179", "192.168.0.22", "192.168.0.4"}, // options
                    {"", "", "", "",} // responses
            },
            {
                    {"What is a device that splits broadcast domain?"},   //question
                    {"edit"},      // Question type: available types ['radio','check','edit']
                    {"Router", "", "", ""}, // answer
                    {"Option 1", "Router", "Option 3 - ans", "Option 4",}, // options
                    {"", "", "", "",} // responses
            },
            {
                    {"What service resolves domain names IP addresses?"},   //question
                    {"radio"},   // Question type: available types ['radio','check','edit']
                    {"DNS", "", "", ""}, // answer
                    {"ARP", "CALEA", "DHCP", "DNS"}, // options
                    {"", "", "", "",} // responses
            }
    };
