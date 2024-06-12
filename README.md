## How to use the application:

This program provides a command-line interface for crawling websites. It allows users to specify the URL without the top-level domain to crawl, top-level domains of the website, the depth, and the language the crawled content should be translated to.

### Prerequisites

- Java Development Kit (JDK) version 19 or later installed on your system.

### Installation
1. Clone this repository
2. Compile the source code

### Usage
1. Run the application
2. You are asked to give the parameters from the command line

### Examples
- Please enter the API Key for Google Translate (rapidapi.com): Enter API Key, if Key is invalid translation will be skipped.
- Please enter an url without the top-level domain e.g. https://www.example or type 'done' to finish
- Enter new url: `https://github`
- Enter new url: `https://oebb`
- Enter new url: `done`
- Please enter a top-level domain (e.g. .com), or type 'done' to finish
- Enter new top-level domain: `.at`
- Enter new top-level domain: `.jp`
- Enter new top-level domain: `done`
- Please enter an integer to define the depth: `1`
- Please enter a target language (e.g. en, ru, de): `ru`

# SimpleJavaWebCrawler

The task of this assignment is to develop a Web-Crawler in Java, which provides a compact overview of the given website and linked websites in a given language by only listing the headings and the links. The attached example-report.md shows an example of how the overview could look (feel free to improve the suggested layout).

## Must have features  
How to start the application:

- your crawler must have a Main class which is the entry point to your application
- after the start, the user must then input arguments to the command line

The crawler MUST implement at least the following features:

- input the URL, the depth of websites to crawl, the domain(s) of websites to be crawled, and the target language to the command line
- create a compact overview of the crawled websites in a specified target language (e.g. translate it into German)
    - record and translate only the headings
    - represent the depth of the crawled websites with proper indentation (see example)
    - record the URLs of the crawled sites
    - highlight broken links
- find the links to other websites and recursively do the analysis for those websites (it is enough if you analyze the pages at a depth of 2 without visiting further links, you might also allow the user to configure this depth via the command line)
- store the results in a single markdown file (.md extension)

Note, also provide automated unit tests for each feature (we will not accept submissions without unit tests).

## New must have features

    New feature to allow concurrent crawling (and optionally also translation) of multiple websites. Given two or more URLs as input to your program, your solution should concurrently gather the data for the websites and their referenced websites. In the end, you should combine the results for each website in a single report while retaining the original structure of the websites.
    Implement proper and clean Error Handling. Various errors might occur when crawling and/or translating a website. Protect your application against crashing by handling the errors and informing the user by logging the error in the report.
    The dependencies on third-party libraries, e.g., jsoup, should be small to allow future updates or even replacements of the third-party libraries. 

Implementation

Keep in mind the guidelines presented in the course and make sure that (most of) your code respects the SRP, OCP, and DIP, or at least keep the violations as small as possible. Regarding the dependencies to third-party libraries, use the principles presented in the book to define and implement these boundaries. Also, provide clean tests to verify that your solution correctly implements the features (make sure to write clean and testable code). As for Assignment 1, the repository needs to contain a README file that briefly states the steps to build, run, and test your crawler. 
