# GoNature Park
This project aims to streamline park operations by implementing a comprehensive system that can manage reservations
the project was implemented in a client-server architecture design, we gave the users the ability to make reservations
at any time they wish, only if there is enough space for the party of the user, To manage the park we created users 
with permissions to help them manage the park, the park manager can send requests to change certain parameters and create
reports according to his wishes, so does the department manager, he can create unique reports for the park and view the 
park manager's reports, as well as approve or deny the parameter update request that the park manager sent, lastly we have
a park employee who can enter and exit visitors from the park, with the ability to enter visitors without reservations.


## Table of Contents

- [Installation](#installation)
- [Documentation](#document)
- [How to run](#how-to-run)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)


## Installation
[Main Database](prototypenew.sql)

this file contains our database to download correctly, create a new schema called prototypenew, and import the file into it.

[Users Data Database](users.sql)

this file contains the saved data of our users, (usernames and passwords and more data), so create a new schema called users, and import the file into it.

all the other folders add them to your IDE.

## Documentation

We encourage the creation of JavaDoc files for the following folders:

- `GNClient`
- `GNServer`

JavaDoc provides a convenient way to generate documentation directly from the source code, making it easier for developers to understand the functionality and usage of classes and methods within these folders.

While our own JavaDoc is generated locally, viewers are encouraged to create and maintain JavaDoc files to get clear insight to our codebase. Our code is clear and understandable as well as our documentation.

For instructions on generating JavaDoc files, please refer to the official [JavaDoc documentation](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html).

## How to run
To run the project the user has to run the [Server Main function](ServerUI.java) of the server first, then run the [Client Main function](ClientUI.java).

- `Server` After running the main function, enter username + password + port: 5555, click on "start server", then click on "Import users" and with that the server is set.
- `Client` After running the main function of the server and client, wait until the server is started, then write the IP of the server (if local then write "localhost"), wait for users to be imported, then enter using either username + password or an ID.

## Contributing
all contributions are not accepted since this is a college project that was created by the students and can be found in the Contact section. However, we encourage you to explore the project's codebase to learn and gain insights.
If you're interested in contributing to similar open-source projects, we recommend exploring other repositories on GitHub and getting involved in the open-source community.
Feel free to reach out to us if you have any questions or need further assistance.


## License
[Braude College of Engineering, Karmiel](https://w3.braude.ac.il/?lang=en).


## Contact

`GitHub`
- [Ahmad Ataba](https://github.com/Ataba29)
- [Mohammad Khateeb](https://github.com/khalelmnsor)
- [Rami Taha]()
- [Ibraheem Jramnh](https://github.com/ibraheemjr21)
- [Khalel Mnsor](https://github.com/khalelmnsor)
- [Jad Taha](https://github.com/jaytaa)
- [Kareem Zeedan](https://github.com/SharkZeedan)



`LinkedIn`
- [Ahmad Ataba](https://www.linkedin.com/in/ahmad-ataba-08111a270/)
- [Mohammad Khateeb]()
- [Rami Taha](https://www.linkedin.com/in/rami-taha-234871273/)
- [Ibraheem Jramnh]()
- [Khalel Mnsor]()
- [Jad Taha](https://www.linkedin.com/in/jad-taha-b1aa23199/)
- [Kareem Zeedan](https://www.linkedin.com/in/kareem-zeedan-81baab273/)

