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
- Please enter a url without the top-level domain
  e.g. https://www.example: `https://aau`
- Please enter a top-level domain (e.g. .com), or type 'done' to finish: `.at`
- Please enter a top-level domain (e.g. .com), or type 'done' to finish: `done`
- Please enter an integer to define the depth: `2`
- Please enter a target language (e.g. en, ru, de): `ru`