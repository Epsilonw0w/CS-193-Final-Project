# Days-Off Calendar

Grabs dates from the Purdue academic calendar to list when long weekends and holidays take place.

## The Problem

My mother mentioned she has a hard time figuring out when I and my siblings' holiday schedules align, so I want to build an MVP to provide her with a clean list of all the potential dates I can come home to visit.

## How It Works

Run a command to execute the program, which leads the scraper to grab data from the calendar website. The next class will read the data from the html, write it to the terminal or (eventually) a .txt file listing the days off in order and the duration of the break. Once finished it will return the .txt file.

![System diagram](docs/System_Diagram.png)

## Tech Stack

- **Language:** Java
- **Libraries:** HttpClient, JSoup
- **External tools:** Purdue yearly calendar HTML
- **Data Storage:** calendar.txt (optional)

## Setup

1. `clone` the repository to your desired directory and `cd` into it.
2. Use `./gradlew run` to run the program.
> Note: Best to use a terminal that runs bash code, as the differences between Linux and Windows terminals may change the initiation steps.

## Usage

`./gradlew run` runs the program. After that, a list of dates will be printed to the terminal, filtered from the original list on the Purdue Calendar site. You will then be given the option to write this data to a .txt file called "Holiday.txt".

## Roadmap

- [ ] Write to organized text file
- [ ] Multi-calendar reading

## Author

Epsilon Spychalski (Github: [Epsilonw0w](https://github.com/Epsilonw0w))
