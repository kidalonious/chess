# My notes 
Phase 1:
- If you are in check, see if there are any valid moves. You can do this by cloning the board and checking each clone
- other method is if you allow yourself to undo a move. you apply a move, then see if you are in check, then redo the move.
- If you find even one valid move that gets you out of check, then you are not in checkmate. You don't need to check every move necessarily.


JSON & Parsing
- structure of documents: Objects, Arrays, Numbers, Strings, Boolean, Null
- Objects are shown like this: {...}
- {"name":"Bob","age":32, "alive":true}
- DOM stands for Document Object Model
- DOM parser allows you to grab elements of arrays easier
- Stream parser doesn't read in the full file, it tokenizes input
- Serializers/Deserializers use a library to convert from JSON to Java Objects, or the other way around
- GSON and Jackson are the most popular
- JSON documents can be parsed and represented as Java Objects
Phase 3:
- If Web API requires authToken, the handler can validate (to be continued)
- "Singleton pattern" create a static getInstance method that always returns the same instance for registerHandlers
- Avoid duplicate code through inheritance hierarchy
- Implement one endpoint at a time
- Parent Response class that you can inherit message from
- UUID.randomUUID().toString() will get a random ID.
Potential Code
- LoginRequest request = (LoginRequest)gson.fromJson(reqData, LoginRequest.class);
- LoginService service = new LoginService();
  public static void joinGame(JoinGameRequest request, String authToken) throws UnauthorizedException, BadRequestException, DuplicateException, DataAccessException{
  if (authMemoryDAO.getAuthData(authToken) == null) {
  throw new UnauthorizedException("unauthorized");
  }
  if (gameMemoryDAO.getGame(request.gameID()) == null) {
  throw new BadRequestException("bad request");
  }
  if ((request.playerColor().equals("WHITE") && gameMemoryDAO.getGame(request.gameID()).whiteUsername() != null)
  || (request.playerColor().equals("BLACK") && gameMemoryDAO.getGame(request.gameID()).blackUsername() != null)) {
  throw new DuplicateException("already taken");
  }
  gameMemoryDAO.joinGame(request, authToken);
  }