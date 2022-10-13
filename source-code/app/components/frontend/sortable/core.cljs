
(ns app.components.frontend.sortable.core
  (:require [mid-fruits.random :as random]

    [reagent.api :refer [ratom]]

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
  [state event]
  (reset! state (get (js->clj (aget event "active")) "id")))

(defn drag-end-f
  [{:keys [items active-id on-drag-end on-order-changed value-path]} event]
  (let [{:keys [active over]} (to-clj-map event)
        oldIndex              (index-of @items active)
        newIndex              (index-of @items over)]
    ;;check if dragged element is moved if so than reset the items order
    (if (not= oldIndex newIndex)
        (let [new-items (reorder @items oldIndex newIndex)]
             (reset! items new-items)
             (if value-path       (r/dispatch [:db/set-item! value-path new-items]))
             (if on-order-changed (r/dispatch (r/metamorphic-event<-params on-order-changed new-items)))))
    (reset! active-id nil)
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

(defn sortable-item
  [{:keys [index id] :as props} item-element]
  (let [sortable (to-clj-map (useSortable (clj->js {:id (str id)})))
        {:keys [attributes listeners setNodeRef isSorting                       ;; pass "attributes" "listeners" to componentn props to make it dragable
                transform transition isDragging]} sortable]
    [:div {:key (str index) :data-grabbing isDragging}
     [:div (merge {:id    (str id)
                   :ref   (js->clj setNodeRef)
                   :style {:transform  (.toString (.-Transform CSS) (clj->js transform))
                           :transition transition}})
       [item-element props sortable]]]))

(defn render-items
  [{:keys [item-id-key item-element value-path items]}]
  [:<> (map-indexed (fn [index props]
                     (let [id (get props item-id-key props)]
                       ;; :f> needed cause useSortable hook
                       [:f> sortable-item
                          {:key id :id id
                           :index index
                           :value-path value-path
                           :item-props props}
                          item-element]))
           @items)])

(defn dnd-context
  [{:keys [items active-id item-element item-id-key] :as config}]
  (let [sensors (useSensors
                  (useSensor PointerSensor)
                  (useSensor TouchSensor))]
    (if (not (empty? @items))
      [DndContext {:sensors            sensors
                   :collisionDetection closestCenter
                   :onDragStart        #(drag-start-f active-id %)
                   :onDragEnd          #(drag-end-f config %)}
        [SortableContext {:items @items
                          :strategy rectSortingStrategy}
          [render-items config]]
        [DragOverlay {:z-index 1} (if @active-id [:div {:style {;:cursor "crosshair"
                                                                ;:background :red
                                                                :cursor :grabbing
                                                                :width "100%"
                                                                :height "100%"}}])]])))

(defn sortable
  [sortable-id {:keys [items] :as sortable-props}]
  ; {:items (vector of (string|map))
  ;  :item-element [reagent-component]
  ;  :item-id-key (keyword)                                                     ;; Needed if items data is map. rendered item id have to be unique
  ;  :value-path  (vector of keyword)
  (let [items-data (ratom items)
        active-id  (ratom nil)]
       [:f> dnd-context (assoc sortable-props :items items-data :active-id active-id)]))

(defn view
  ; @param (keyword)(opt) sortable-id
  ; @param (map) sortable-props
  ;  {:item-element (metamorphic-content)
  ;   :on-drag-end (metamorphic-event)(opt)}
  ;   :on-drag-start (metamorphic-event)(opt)}
  ;   :on-order-changed (metamorphic-event)(opt)
  ;    Az esemény utolsó paraméterként megkapja az ... (?)}
  ;
  ; @usage
  ;  (defn my-item-element [])
  ;  []
  ([sortable-props]
   [view (random/generate-keyword) sortable-props])

  ([sortable-id sortable-props]
   [sortable sortable-id sortable-props]))
