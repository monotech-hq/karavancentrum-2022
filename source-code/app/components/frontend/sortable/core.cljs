
(ns app.components.frontend.sortable.core
  (:require [mid-fruits.random :as random]


            [reagent.api :as reagent :refer [ratom]]

            [app.components.frontend.sortable.helpers :as helpers]
            [app.components.frontend.sortable.state :as state]



    [re-frame.api :as r]
    [x.app-elements.api :as elements]

    [app.components.frontend.sortable.utils :refer [to-clj-map
                                                    vec-remove
                                                    reorder
                                                    index-of]]
    [app.components.frontend.sortable.dndkit :refer [useSortable
                                                     CSS
                                                     useSensors
                                                     useSensor
                                                     PointerSensor
                                                     MouseSensor
                                                     TouchSensor
                                                     DndContext
                                                     closestCenter
                                                     SortableContext
                                                     DragOverlay
                                                     rectSortingStrategy
                                                     arrayMove]]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn drag-start-f
  [sortable-id _ event]
  (swap! state/GRABBED-ITEM assoc sortable-id (get-in (js->clj (aget event "active")) ["data" "current" "sortable" "index"])))

(defn drag-end-f
  [sortable-id {:keys [on-drag-end on-order-changed value-path]} event]
  (let [{:keys [active over]} (to-clj-map event)
        oldIndex              (index-of (get @state/SORTABLE-ITEMS sortable-id) active)
        newIndex              (index-of (get @state/SORTABLE-ITEMS sortable-id) over)]
    ;;check if dragged element is moved if so than reset the items order
    (if (not= oldIndex newIndex)
        (let [new-items (reorder (get @state/SORTABLE-ITEMS sortable-id) oldIndex newIndex)]
             (swap! state/SORTABLE-ITEMS assoc sortable-id new-items)
             (if value-path       (r/dispatch [:db/set-item! value-path new-items]))
             (if on-order-changed (r/dispatch (r/metamorphic-event<-params on-order-changed new-items)))))
    (swap! state/GRABBED-ITEM dissoc sortable-id)
    (if on-drag-end (r/dispatch on-drag-end))))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(r/reg-event-db :sortable/remove!
  (fn [db [_ value-path index]]
    (let [items (get-in db value-path)
          result (vec-remove items index)]
      (assoc-in db value-path result))))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn- sortable-debug
  [sortable-id _]
  [:div {}
        [:br] (str "GRABBED-ITEM:   " (get @state/GRABBED-ITEM   sortable-id))
        [:br] (str "SORTABLE-ITEMS: " (get @state/SORTABLE-ITEMS sortable-id))])

(defn sortable-item
  [sortable-id item-dex {:keys [id] :as props} item-element]
  ;; pass "attributes" "listeners" to component props to make it dragable
  ; {:keys [attributes listeners setNodeRef isSorting transform transition isDragging]} sortable]
  (let [sortable (to-clj-map (useSortable (clj->js {:id (str id)})))
        {:keys [setNodeRef transform transition]} sortable]
    [:div {:ref   (js->clj setNodeRef) ;:id (str id)
           :key   (str item-dex)
           :style {:transition transition :transform (.toString (.-Transform CSS) (clj->js transform))}}
          [item-element sortable-id item-dex props sortable]]))

(defn render-items
  [sortable-id {:keys [item-id-key item-element]}]
  (letfn [(f [%1 %2 %3] ;; :f> needed cause useSortable hook
             (let [id (get %3 item-id-key %3)]
                  (conj %1 ^{:key id}
                            [:f> sortable-item sortable-id %2
                                               {:id id :item-props %3}
                                               item-element])))]
         (reduce-kv f [:<>] (get @state/SORTABLE-ITEMS sortable-id))))

(defn dnd-context
  [sortable-id sortable-props]
  (let [sensors (useSensors (useSensor PointerSensor)
                            (useSensor TouchSensor))]
    (if (-> @state/SORTABLE-ITEMS sortable-id empty? not)
        [DndContext {:sensors            sensors
                     :collisionDetection closestCenter
                     :onDragStart        #(drag-start-f sortable-id sortable-props %)
                     :onDragEnd          #(drag-end-f   sortable-id sortable-props %)}
          [SortableContext {:items    (get @state/SORTABLE-ITEMS sortable-id)
                            :strategy rectSortingStrategy}
                           [render-items sortable-id sortable-props]]
          [DragOverlay {:z-index 1} (if (get @state/GRABBED-ITEM sortable-id)
                                        [:div {:style {:cursor :grabbing :width "100%" :height "100%"}}])]])))

(defn sortable-body
  [sortable-id sortable-props]
  ; {:items (vector of (string|map))
  ;  :item-element [reagent-component]
  ;  :item-id-key (keyword)                                                     ;; Needed if items data is map. rendered item id have to be unique
  ;  :value-path  (vector of keyword)
  [:<> [:f> dnd-context sortable-id sortable-props]
       [sortable-debug  sortable-id sortable-props]])

(defn sortable
  ; @param (keyword) sortable-id
  ; @param (map) sortable-props
  [sortable-id sortable-props]
  (reagent/lifecycles {:reagent-render         (fn [_ _] [sortable-body                   sortable-id sortable-props])
                       :component-did-mount    (fn [_ _] (helpers/sortable-did-mount-f    sortable-id sortable-props))
                       :component-will-unmount (fn [_ _] (helpers/sortable-will-unmount-f sortable-id sortable-props))
                       :component-did-update   (fn [this] (let [[_ sortable-props] (reagent/arguments this)]
                                                               (helpers/sortable-did-update-f sortable-id sortable-props)))}))

(defn body
  ; @param (keyword)(opt) sortable-id
  ; @param (map) sortable-props
  ;  {:item-element (metamorphic-content)
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
  ;  (defn my-item-element [sortable-id item-dex item])
  ;  [sortable/body {:item-element #'my-item-element
  ;                  :items ["My item" "Your item"]}]
  ([sortable-props]
   [body (random/generate-keyword) sortable-props])

  ([sortable-id sortable-props]
   [sortable sortable-id sortable-props]))
