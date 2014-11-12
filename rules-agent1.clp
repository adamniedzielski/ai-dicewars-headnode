; this is inserted from java
(defglobal ?*myPlayerNumber* = 1)

(deffunction pm-vertex-neighbour-from-to-differ (?vn)
   (not(eq (fact-slot-value ?vn vertex) (fact-slot-value ?vn neighbour))))
 
(deffunction pm-vertex-more-than-one-dice (?v)
   (not(eq (fact-slot-value ?v dices) 1)))
   
(deffunction pm-vertex-neighbour-player-differ(?v1 ?v2)
   (not(eq (fact-slot-value ?v1 playerNumber) (fact-slot-value ?v2 playerNumber))))

(deffunction pm-vertex-mine (?v)
   (eq (fact-slot-value ?v playerNumber) ?*myPlayerNumber* ))

(defrule move-is-possible
		(declare (salience 10000))
        ?vn <- (vertex-neighbour(vertex ?from) (neighbour ?to))
		?v-from <- (vertex(id ?from))
		?v-to <- (vertex(id ?to))
		;not needed as in vertex-neighbour always vertex != neighbour
		(test (pm-vertex-neighbour-from-to-differ ?vn))
        (test (pm-vertex-more-than-one-dice ?v-from))
		(test (pm-vertex-neighbour-player-differ ?v-from ?v-to))
		(test (pm-vertex-mine ?v-from))
		(test (not (pm-vertex-mine ?v-to)))
        => 
        (assert(possible-move(from ?from) (to ?to))) 
) 
	
(defrule act-if-possible
	(declare (salience 9000))
	(exists (possible-move))
	=> 
	(assert(function is now called)))


;(assert (next-move (from 0)(to 0)(isEmptyMove true)))
;(assert (next-move (from 9)(to 9)(isEmptyMove true)))

	
(deffacts sample-facts
(vertex-neighbour (vertex 1) (neighbour 2))
(vertex-neighbour (vertex 1) (neighbour 3))
(vertex-neighbour (vertex 1) (neighbour 4))
(vertex-neighbour (vertex 2) (neighbour 4))
(vertex (id 1) (dices 2) (playerNumber 1))
(vertex (id 2) (dices 1) (playerNumber 2))
(vertex (id 3) (dices 1) (playerNumber 1)) 
(vertex (id 4) (dices 5) (playerNumber 2))     
)

;(assert (vertex-neighbour (vertex 1)(neighbour 2)))
;(assert(vertex(id 1)(dices 2)(playerNumber 1)))
;(assert(next-move(from 1)(to 2)(isEmptyMove false)))

;		(printout t "#### RUN -DEBUG MESSAGE " crlf)
