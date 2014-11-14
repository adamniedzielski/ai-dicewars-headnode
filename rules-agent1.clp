(deffunction pm-vertex-more-than-one-dice (?v)
   (not(eq (fact-slot-value ?v dices) 1)))
   
(deffunction pm-vertex-neighbour-player-differ(?v1 ?v2)
   (not(eq (fact-slot-value ?v1 playerNumber) (fact-slot-value ?v2 playerNumber))))

(deffunction pm-vertex-mine (?v)
   (eq (fact-slot-value ?v playerNumber) ?*myPlayerNumber* ))

(defrule move-is-possible
  (vertex-neighbour(vertex ?from) (neighbour ?to))
	?v-from <- (vertex(id ?from))
	?v-to <- (vertex(id ?to))
  (test (pm-vertex-more-than-one-dice ?v-from))
	(test (pm-vertex-neighbour-player-differ ?v-from ?v-to))
	(test (pm-vertex-mine ?v-from))
	(test (not (pm-vertex-mine ?v-to)))
  => 
  (assert(possible-move(from ?from) (to ?to))))

(defrule make-move
  (possible-move(from ?from) (to ?to))
  (not (move-selected))
  =>
  (assert (next-move (from ?from)(to ?to)(isEmptyMove false)))
  (assert (move-selected)))

(defrule empty-move
  (declare (salience -1000))
  (not (move-selected))
  =>
  (assert (next-move (from 0)(to 0)(isEmptyMove true))))