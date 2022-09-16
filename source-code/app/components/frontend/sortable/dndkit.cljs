
(ns app.components.frontend.sortable.dndkit
  (:require
    [reagent.core :as r :refer [atom]]
    ["@dnd-kit/core"      :as core]
    ["@dnd-kit/sortable"  :as sortable]
    ["@dnd-kit/utilities" :as utilities]))

(def closestCenter core/closestCenter)
(def closestCorners core/closestCorners)
(def TouchSensor   core/TouchSensor)
(def MouseSensor   core/MouseSensor)
(def PointerSensor core/PointerSensor)
(def useSensor     core/useSensor)
(def useSensors    core/useSensors)

(def arrayMove           sortable/arrayMove)
(def rectSortingStrategy sortable/rectSortingStrategy)
(def useSortable         sortable/useSortable)

(def CSS utilities/CSS)

(def DndContext      (r/adapt-react-class core/DndContext))
(def DragOverlay     (r/adapt-react-class core/DragOverlay))
(def SortableContext (r/adapt-react-class sortable/SortableContext))
