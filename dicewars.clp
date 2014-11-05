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
	
	
(deftemplate player
   (slot playerNumber)
   (slot who     
    (type SYMBOL)
    (allowed-symbols me enemy)
    (default enemy)))
	 

;NOTES 
;(assert (vertex(id 1)(dices 1)(player 1)))

;(find-all-facts ((?v next-move))(eq 1 1))
;(deffacts( myPlayerNumber 1))
;(deffacts myPlayerNumber(player (playerNumber 1)(who me)))