
(ns app.components.frontend.sortable.core
  (:require

    [reagent.core :as r :refer [atom]]

    [x.app-core.api :as a]
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
;; ---- Events ----

(defn drag-start [state event]
  (reset! state (get (js->clj (aget event "active")) "id")))

(defn drag-end [{:keys [items active-id value-path]} event]
  (let [{:keys [active over]} (to-clj-map event)]
   (let [oldIndex (index-of @items active)
         newIndex (index-of @items over)]
     ;;check if dragged element is moved if so than reset the items order
     (if (not= oldIndex newIndex)
       (let [new-items (reorder @items oldIndex newIndex)]
         (reset! items new-items)
         (if value-path
           (a/dispatch [:db/set-item! value-path new-items]))))
     (reset! active-id nil))))

;; ---- Events ----
;; -----------------------------------------------------------------------------

(a/reg-event-db
  :sortable/remove!
  (fn [db [_ value-path index]]
    (let [items (get-in db value-path)
          result (vec-remove items index)]
      (assoc-in db value-path result))))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn sortable-item [{:keys [index id] :as props} item]
  (let [sortable (to-clj-map (useSortable (clj->js {:id id})))
        {:keys [attributes listeners setNodeRef isSorting                       ;; pass "attributes" "listeners" to componentn props to make it dragable
                transform transition isDragging]} sortable]
    [:div {:key index :data-grabbing isDragging}
     [:div (merge {:id    (str id)
                   :ref   (js->clj setNodeRef)
                   :style {:transform  (.toString (.-Transform CSS) (clj->js transform))
                           :transition transition}})
       [item props sortable]]
     [:p {:style {:text-align "center" :margin-top "6px"}} (str (inc index))]]))

(defn render-items [{:keys [item-id-key item value-path items]}]
  [:<> (map-indexed (fn [index props]
                     (let [id (get props item-id-key props)]
                       ;; :f> needed cause useSortable hook
                       [:f> sortable-item
                          {:key id :id id
                           :index index
                           :value-path value-path
                           :item-props props}
                          item]))
           @items)])

(defn dnd-context [{:keys [items active-id item item-id-key] :as config}]
  (let [sensors (useSensors
                  (useSensor PointerSensor)
                  (useSensor TouchSensor))]
    (if (not (empty? @items))
      [DndContext {:sensors            sensors
                   :collisionDetection closestCenter
                   :onDragStart        #(drag-start active-id %)
                   :onDragEnd          #(drag-end config %)}
        [SortableContext {:items @items
                          :strategy rectSortingStrategy}
          [render-items config]]
        [DragOverlay {:z-index 1} (if @active-id [:div {:style {:cursor "crosshair"}}])]])))

(defn sortable [{:keys [items] :as config}]
  (let [items-data (atom items)
        active-id  (atom nil)]
    [:f> dnd-context (assoc config :items items-data :active-id active-id)]))

(defn view [config]
  [sortable config])
