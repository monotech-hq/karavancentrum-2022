
(ns app.components.frontend.sortable.core
  (:require ["@dnd-kit/core"      :as dndkit.core]
            ["@dnd-kit/sortable"  :as dndkit.sortable]
            ["@dnd-kit/utilities" :as dndkit.utilities]
            [mid-fruits.random    :as random]
            [reagent.api          :as reagent :refer [ratom]]

            [app.components.frontend.sortable.helpers    :as helpers]
            [app.components.frontend.sortable.prototypes :as prototypes]
            [app.components.frontend.sortable.state      :as state]

            [app.components.frontend.sortable.utils :refer [to-clj-map
                                                            reorder
                                                            index-of]]))


;; -- Redirects ----------------------------------------------------------------
;; -----------------------------------------------------------------------------

; @dnd-kit/core
(def closestCenter  dndkit.core/closestCenter)
(def closestCorners dndkit.core/closestCorners)
(def TouchSensor    dndkit.core/TouchSensor)
(def MouseSensor    dndkit.core/MouseSensor)
(def PointerSensor  dndkit.core/PointerSensor)
(def useSensor      dndkit.core/useSensor)
(def useSensors     dndkit.core/useSensors)

; @dnd-kit/sortable
(def arrayMove           dndkit.sortable/arrayMove)
(def rectSortingStrategy dndkit.sortable/rectSortingStrategy)
(def useSortable         dndkit.sortable/useSortable)

; @dndkit.utilities
(def CSS dndkit.utilities/CSS)



;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(def DndContext      (reagent/adapt-react-class dndkit.core/DndContext))
(def DragOverlay     (reagent/adapt-react-class dndkit.core/DragOverlay))
(def SortableContext (reagent/adapt-react-class dndkit.sortable/SortableContext))



;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- sortable-debug
  [sortable-id _]
  [:div {}
        [:br] (str "GRABBED-ITEM:   " (get @state/GRABBED-ITEM   sortable-id))
        [:br] (str "SORTABLE-ITEMS: " (get @state/SORTABLE-ITEMS sortable-id))])

(defn- sortable-item
  ; WARNING! NON-PUBLIC! DO NOT USE!
  ;
  ; @param (keyword) sortable-id
  ; @param (map) sortable-props
  ;  {:item-element (metamorphic-content)
  ;   :item-id-f (function)}
  ; @param (integer) item-dex
  ; @param (*) item
  [sortable-id {:keys [item-id-f item-element]} item-dex item]
  (let [sortable (to-clj-map (useSortable (clj->js {:id (item-id-f item)})))
        {:keys [setNodeRef transform transition]} sortable]
    [:div {:ref   (js->clj   setNodeRef) ;:id (str id)
           :key   (item-id-f item)
           :style {:transition transition :transform (.toString (.-Transform CSS) (clj->js transform))}}
          [item-element sortable-id item-dex item sortable]]))

(defn- render-items
  ; WARNING! NON-PUBLIC! DO NOT USE!
  ;
  ; @param (keyword) sortable-id
  ; @param (map) sortable-props
  [sortable-id sortable-props]
  ; :f> needed cause useSortable hook
  (letfn [(f [item-list item-dex item]
             (conj item-list [:f> sortable-item sortable-id sortable-props item-dex item]))]
         (reduce-kv f [:<>] (get @state/SORTABLE-ITEMS sortable-id))))

(defn- dnd-context
  ; WARNING! NON-PUBLIC! DO NOT USE!
  ;
  ; @param (keyword) sortable-id
  ; @param (map) sortable-props
  [sortable-id sortable-props]
  (let [sensors (useSensors (useSensor PointerSensor)
                            (useSensor TouchSensor))]
       (if (-> @state/SORTABLE-ITEMS sortable-id empty? not)
           [DndContext {:sensors            sensors
                        :collisionDetection closestCenter
                        :onDragStart        #(helpers/drag-start-f sortable-id sortable-props %)
                        :onDragEnd          #(helpers/drag-end-f   sortable-id sortable-props %)}
                       [SortableContext {:items (get @state/SORTABLE-ITEMS sortable-id)
                                         :strategy rectSortingStrategy}
                                        [render-items sortable-id sortable-props]]
                       [DragOverlay {:z-index 1} (if (get @state/GRABBED-ITEM sortable-id)
                                                     [:div {:style {:cursor :grabbing :width "100%" :height "100%"}}])]])))

(defn- sortable-body
  ; WARNING! NON-PUBLIC! DO NOT USE!
  ;
  ; @param (keyword) sortable-id
  ; @param (map) sortable-props
  [sortable-id sortable-props]
  [:<> [:f> dnd-context sortable-id sortable-props]])
      ;[sortable-debug  sortable-id sortable-props]

(defn sortable
  ; WARNING! NON-PUBLIC! DO NOT USE!
  ;
  ; @param (keyword) sortable-id
  ; @param (map) sortable-props
  [sortable-id sortable-props]
  (reagent/lifecycles {:component-did-mount    (fn [_ _] (helpers/sortable-did-mount-f    sortable-id sortable-props))
                       :component-will-unmount (fn [_ _] (helpers/sortable-will-unmount-f sortable-id sortable-props))
                       :component-did-update   (fn [%]   (helpers/sortable-did-update-f   sortable-id %))
                       :reagent-render         (fn [_ _] [sortable-body                   sortable-id sortable-props])}))

(defn body
  ; @param (keyword)(opt) sortable-id
  ; @param (map) sortable-props
  ;  {:item-element (metamorphic-content)
  ;   :item-id-f (function)(opt)
  ;    Default: return
  ;   :items (vector)
  ;   :on-drag-end (metamorphic-event)(opt)}
  ;   :on-drag-start (metamorphic-event)(opt)}
  ;   :on-order-changed (metamorphic-event)(opt)
  ;    Az esemény utolsó paraméterként megkapja az ... (?)}
  ;
  ; @usage
  ;  [sortable/body {...}]
  ;
  ; @usage
  ;  [sortable/body :my-sortable {...}]
  ;
  ; @usage
  ;  (defn my-item-element [sortable-id item-dex item dndkit-props])
  ;  [sortable/body {:item-element #'my-item-element
  ;                  :items ["My item" "Your item"]}]
  ([sortable-props]
   [body (random/generate-keyword) sortable-props])

  ([sortable-id sortable-props]
   (let [sortable-props (prototypes/sortable-props-prototype sortable-props)]
        [sortable sortable-id sortable-props])))
