(deftemplate vertex-neighbour
   (slot vertex)
   (slot neighbour))
   
(deftemplate vertex
   (slot id)
   (slot dices)
   (slot playerNumber))
   
(deftemplate next-move
   (slot from)
   (slot to)
   (slot isEmptyMove     
    (type SYMBOL)
    (allowed-symbols true false)
    (default false)))

(deftemplate possible-move
    (slot from)
	(slot to))	

;NOTES 
;(assert (vertex(id 1)(dices 1)(player 1)))
;(find-all-facts ((?v next-move))(eq 1 1))
