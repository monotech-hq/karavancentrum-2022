
(ns app.contents.frontend.tabs.brands.core
  (:require
    [reagent.core :refer [atom]]
    [x.app-core.api       :as a]
    [x.app-elements.api   :as elements]
    [app.components.frontend.api :as comp]

    [app.contents.frontend.tabs.brands.events]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

;; -----------------------------------------------------------------------------
;; ---- Editable item ----

(defn select-photo [index]
  [:button {:style {:margin-top "14px"}
            :on-click  #(a/dispatch [:storage.media-selector/load-selector! :vehicles.editor/thumbnail-selector
                                     {:value-path [:contents :config-handler/edited-item :brands index :logo]}])}
   [elements/icon {:icon :photo_library :color :muted}]])

(defn logo-field [state {:keys [item-props index] :as props}]
  [:div {:style {:display "flex" :align-items "center" :gap "6px"}}
   [elements/text-field
    {:initial-value (:logo item-props)
     :label         :image
     :on-change     [:contents.brands/on-type-ended state [:logo]]
     :placeholder   "Kép link"}]
   [select-photo index]])

(defn label-field [state {:keys [label] :as props}]
  [elements/text-field
   {:initial-value label
    :label :name
    :on-change   [:contents.brands/on-type-ended state [:label]]
    :placeholder "Név"}])

(defn uri-field [state {:keys [uri] :as props}]
  [elements/text-field
   {:initial-value uri
    :label :link
    :on-change   [:contents.brands/on-type-ended state [:uri]]
    :placeholder "Link"}])

(defn edit-brand [state {:keys [item-props] :as props}]
  [:div {:style {:display "flex" :flex-direction "column" :gap "8px" :justify-content "center" :margin-top "8px" :height "220px"}}
   [logo-field state props]
   [label-field state item-props]
   [uri-field state item-props]])

;; ---- Editable item ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Item preview ----

(defn brand [{:keys [item-props] :as props} {:keys [attributes listeners]}]
  (let [{:keys [logo label uri]
         :or {logo  "/images/broken-image.svg"
              label :empty
              uri   :empty}}item-props]
    [:div {:style {:display "flex" :flex-direction "column" :gap "8px" :margin-top "8px"
                   :height "220px"}}
      [:div {:style {:background-color "#ececec"
                     :backgroundSize     "contain"
                     :background-repeat  "no-repeat"
                     :backgroundPosition "center"
                     :backgroundImage (str "url("logo")")
                     :aspect-ratio "16/9"}}]

      [elements/label {:content label}]
      [elements/label {:content uri}]]))

;; ---- Item preview ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Controls ----

(defn save-btn [state {:keys [index] :as props} edit?]
 [:button {:on-click #(do
                        (a/dispatch [:contents.brands/save! index (dissoc @state :edit?)])
                        (reset! edit? false))}
  [elements/icon {:icon :save :color :primary}]])

(defn delete-btn [{:keys [index] :as props}]
  [:button {:on-click #(a/dispatch [:contents.brands/remove! index])}
   [elements/icon {:icon :delete_outline :color :warning}]])

(defn edit-btn [edit?]
  [:button {:on-click #(reset! edit? true)}
   [elements/icon {:icon :edit :color :muted}]])

(defn drag-btn [{:keys [attributes listeners isDragging] :as sortable-props}]
  [:div (merge {:class "contents--brand-card-drag-handle"}
               attributes listeners)
   [elements/icon {:icon :drag_indicator
                   :color (if isDragging :primary :muted)}]])

(defn controls [state props edit? sortable-props]
  [:div {:style {:display "flex" :justify-content "flex-end" :gap "5px"}}
   (if @edit?
     [:<>
      [save-btn state props edit?]
      [delete-btn props]]
     [:<>
      [edit-btn edit?]
      [drag-btn sortable-props]])])

;; ---- Controls ----
;; -----------------------------------------------------------------------------

(defn card [{:keys [item-props] :as props} sortable-props]
  (let [edit? (atom (get-in props [:item-props :edit?] false))
        state (atom item-props)]
    (fn [props sortable-props]
      [:div {:class "contents--brand-card"
             :data-edit @edit?
             :style {:border-radius "10px"
                     :padding "12px"
                     :background "#ececec"}}
       [controls state props edit? sortable-props]
       (if @edit?
         [edit-brand state props]
         [brand props sortable-props])])))

(defn add-brand [props]
   [:button {:on-click #(a/dispatch [:contents.brand/create!])}
     [:div {:style {;:border "2px solid gray"
                    :border-radius "10px"
                    :display "flex" :align-items "center" :justify-content "center"
                    :padding "15px"
                    :margin "0 auto"
                    :width "200px"
                    :aspect-ratio "1/1"
                    :background-color "#ececec"
                    :background-image "url(/images/logo.png)"
                    :backgroundSize     "contain"
                    :background-repeat  "no-repeat"
                    :backgroundPosition "center"}}
       [elements/icon {:icon :add :size :xxl :style {:font-size "50px"}}]]])

(defn brands-tab []
  (let [items @(a/subscribe [:db/get-item [:contents :config-handler/edited-item :brands]])]
    [:div
     [:div {:style {:display "grid" :gap "25px" :grid-template-columns "repeat(auto-fill, minmax(200px, 1fr))"}}
       [comp/sortable {:items      items
                       :value-path [:contents :config-handler/edited-item :brands]
                       :item       #'card
                       :item-id-key :id}]
       [add-brand]]]))

(defn view []
  [brands-tab])

;; ---- Components ----
;; -----------------------------------------------------------------------------
