Solves a numeric puzzle of nine pieces with the selected algorithm

Accepted parameters:
--verbose		Verbose mode - echo everything
-v			Alias for --verbose

--interactive		Interactive mode
-it			Alias for --interactive

--type=number		Type of algorithm to use for solving
-t=number		Alias for --type

--puzzle		Pass the numbers to mount the initial game state
-p		Alias for --puzzle

--help			Shows this help
-h			Alias for --help

Options:
1 - Search with NO information, like a brute force
2 - Search with some information

Examples:
java -cp NinePiecesPuzzle.jar br.feevale.jpe.npp.NinePiecesPuzzle -t=1
java -cp NinePiecesPuzzle.jar br.feevale.jpe.npp.NinePiecesPuzzle --type=2
java -cp NinePiecesPuzzle.jar br.feevale.jpe.npp.NinePiecesPuzzle -v -t=2 -p 6 1 4 7 5 2 3 0 8

