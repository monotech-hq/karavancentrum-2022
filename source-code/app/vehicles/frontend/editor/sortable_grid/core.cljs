
(ns app.vehicles.frontend.editor.sortable-grid.core
  (:require
    [reagent.core :as r :refer [atom]]
    [x.app-core.api :as a]

    [app.vehicles.frontend.editor.sortable-grid.utils :refer [to-clj-map]]
    [app.vehicles.frontend.editor.sortable-grid.dndkit :refer [useSortable
                                                               CSS
                                                               useSensors
                                                               useSensor
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
   (let [oldIndex (.indexOf @items (:id active))
         newIndex (.indexOf @items (:id over))]
     ;;check if dragged element is moved if so than reset the items order
     (if (not= oldIndex newIndex)
       (let [new-items (arrayMove (clj->js @items) oldIndex newIndex)]
         (reset! items new-items)
         (if value-path
           (a/dispatch [:db/set-item! value-path new-items]))))

     (reset! active-id nil))))


;; ---- Events ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn sortable-item [{:keys [index id] :as props} item]
  (let [sortable (to-clj-map (useSortable (clj->js {:id id})))
        {:keys [attributes listeners setNodeRef transform transition isDragging]} sortable]

    [:div {:key index}
      [:div
       [:div (merge {:id    (str id)
                     :ref   (js->clj setNodeRef)
                     :style {:transform (.toString (.-Transform CSS) (clj->js transform))
                             :transition transition
                             :cursor "grab"}}
                attributes
                listeners)
        [item props]]]
     [:p {:style {:text-align "center" :margin-top "6px"}} (str (inc index))]]))

(defn dnd-context [{:keys [items active-id item] :as config}]
  (let [sensors (useSensors
                  (useSensor MouseSensor)
                  (useSensor TouchSensor))]
    (if (not (empty? @items))
      [DndContext {:sensors            sensors
                   :collisionDetection closestCenter
                   :onDragStart        #(drag-start active-id %)
                   :onDragEnd          #(drag-end config %)}
        [SortableContext {:items @items
                          :strategy rectSortingStrategy}
         [:div {:style {:display "grid" :gap "25px" :grid-template-columns "repeat(auto-fill, minmax(200px, 1fr))"}}
          (map-indexed (fn [index props]
                        (let [id (:id props props)]
                          ;; :f> needed to dnd work
                          [:f> sortable-item
                             {:key id :id id :index index :item-props props}
                           item]))

            @items)]]
        [DragOverlay [:div]]])))

(defn grid [{:keys [items] :as config}]
  (let [items     (atom items)
        active-id (atom nil)]
    [:f> dnd-context (assoc config :items items :active-id active-id)]))

(defn view [config]
  [grid config])
