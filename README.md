# apron.squatter

  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                  ARTIFICIAL INTELLIGENCE - PROJECT 1


   Andre Peric Tavares (706525), Felipe Matheus Costa Silva (706279)
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


Table of Contents
─────────────────

1 TODO Pseudocode
2 Complexity


1 TODO Pseudocode
═════════════════

  The used procedure is based on the flood fill algorithm.

  It creates an array of unchecked enclosed pieces and iterates over it.

  The algorithm takes the first unchecked element and adds it to a
  queue.

  Then it removes the first element of the queue, adding it to the
  region, removing it from the unchecked enclosed array and enqueues all
  adjacent pieces of that element that are enclosed and have not been
  visited yet (that is, the ones that don't belong to either the queue
  or to the unchecked elements array).

  It keeps consuming the first element of the queue and repeating the
  process until the queue is empty. At this point, an complete enclosed
  region was detected. This region is added to an array of regions.

  The same process is done again, taking the first element of the
  unchecked enclosed pieces array (if not empty) to detect a new region.

  After that, the algorithm count the score by verifying the colour of
  piece placed on the square above the first piece of each region
  array. If the colour is black, white scores and vice versa.  We can do
  that because

  • pieces that are placed on the edges of the board are not enclosed,
    as it is impossible to enclosed them in a loop. So there will never
    be an enclosed piece on the first row (which would make verifying
    the piece placed on square above impossible).
  • the first element of each region array is the first that appears
    from left to right and top to bottom on the board in that
    region. This happens because we constructed the array that holds the
    unchecked enclosed pieces in that order.

  These two statements combined imply that there will always be a square
  above the first piece in each region and that square contains a non
  captured piece. The second item ensures that the first element of each
  region is the first element of each region array as well. Thus, we can
  safely iterate over the array of regions, verifying the color of the
  piece placed on the square above each array’s first element.

  Print statements and boundary checks are omitted from the pseudocode
  below.
  ┌────
  │ scan the board
  │ for all pieces in board
  │     if (piece is enclosed) unchecked_enclosed_pieces.append(piece)
  │     if (square is empty) exit
  │ end
  │ 
  │ regions_array = Ø // it's an array of arrays, each array represents an enclosed region
  │ while (unchecked_enclosed_pieces != Ø)
  │     R = Ø // represents current region
  │     Q = Ø // queue
  │     *piece* unchecked_enclosed_pieces.first
  │     Q.append(*piece*)
  │ 
  │     while Q != Ø 
  │         *current_piece* = Q.first
  │         Q.pop
  │         R.append(*current_piece*)
  │         unchecked_enclosed_pieces.removeElement(*current_piece*)
  │         for (*neighbor*: *current_piece*.adjacentPiece)
  │             if (*neighbor* is "-" and *neighbor* ∉ (Q U R))
  │                 Q.append(*neighbor*)
  │             end
  │         end
  │     regions_array.append(R)
  │ end
  │ 
  │ white_score = 0
  │ black_score = 0
  │ for (region : regions_array)
  │     if (color of piece placed on square above the first piece of region == black)
  │         black_score++
  │     else
  │         white_score++
  │     end
  │ end
  └────


2 Complexity
════════════

  Let n be the number of squares in the board.

  Scanning the board is clearly O(n).

  Checking the scores is clearly O(n) as well as the number of regions
  is lesser than n.

  We analyse the second block now. This block contains two loops. The
  first one iterates over unchecked_enclosed_pieces and the second one
  over the queue. Inside these blocks, all operations (append, pop, etc)
  take constant time, except removing an element from the
  unchecked_enclosed_pieces array, which is O(n).

  The fewer enclosed pieces, the faster creating the arrays that
  represents regions will be, because less iterations (on the array of
  enclosed pieces and the queue; plus searching the element to be
  removed from unchecked_enclosed_pieces) will run. So the worst case
  happens when the maximum possible number of pieces is enclosed, that
  is, all but the pieces on the edge are captured. In this case, there
  are (n -4*sqrt(n) +4) enclosed pieces. Each piece will be visited and
  a set of instructions will be executed. All operations (append, pop,
  etc) take constant time, except removing an element from the
  unchecked_enclosed_pieces array, which is O(n). But as we remove all
  the enclosed pieces from the unchecked_enclosed_pieces array, the loop
  that checks its value will run just once. Therefore this computation
  is linear on n.

  We conclude that the worst case complexity is O(n).

  The best case is O(n) as well as it consists in just scanning the
  board, verifying that the list of enclosed pieces is empty and
  assigning zero to the scores.
