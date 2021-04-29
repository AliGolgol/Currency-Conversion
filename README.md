
## Description:
I used **JavaMoney** that uses exchange rate from a publicly available.


- Will it work with a 2 TB input file as well?
  - No, but I think Gson is a very popular API for parsing JSON strings into Objects. The parse method, provided by Gson is good for reading the entire JSON string and parse it into Java Objects in one go. To do that the JSON string is first loaded into memory and then converted into an object. However, when we are dealing with a very large JSON file, it can lead to OutOfMemoryError. To avoid this, we can use Gson Streaming technique to parse a large file in chunks.
  - Instead of  using exchange rates from a publicly available service for each line, I think it would be a good idea get
    exchange rates first and create a HashTable, then ues it to calculate currency conversion.
    
- What would happen if the input file has one malformed JSON line towards the
end of the file?
  - I did not handle the malformed Json line. If I had more time I would use JSON Schema validation to handle it.
  
- Assume your API should degrade gracefully / still be available in case the
upstream exchange rate service is down. How would you handle this?
  
- I will use resilient patterns such as Timeout, Retry and Circuit Breaker.

### How to run API & CLI:

### API:

- To run it use this command: ``sudo docker build -t api .``
- Then we run the docker image: ``sudo docker run -p 8010:8010 api``
- The address of rest api is: ``http://loclahost:8010``

### CLI:

- Run first command in the CLI root module: `` mvn clean compile assembly:single  ``
- Then ``java -jar target/CLI-jar-with-dependencies.jar --file /Users/.../Desktop/input.json --t EUR``
  
**Consider the path argument for the --file option is absolut address**
