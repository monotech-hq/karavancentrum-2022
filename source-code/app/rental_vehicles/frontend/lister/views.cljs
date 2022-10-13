
(ns app.rental-vehicles.frontend.lister.views
    (:require [app.common.frontend.api :as common]
              [layouts.surface-a.api   :as surface-a]
              [plugins.item-lister.api :as item-lister]
              [re-frame.api            :as r]
              [x.app-elements.api      :as elements]
              [app.components.frontend.api]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- footer
  []
  (if-let [first-data-received? @(r/subscribe [:item-browser/first-data-received? :rental-vehicles.lister])]
          [common/item-lister-download-info :rental-vehicles.lister {}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- vehicle-item-structure
  [lister-id item-dex {:keys [modified-at name thumbnail]}]
  (let [timestamp @(r/subscribe [:activities/get-actual-timestamp modified-at])]
       [common/list-item-structure lister-id item-dex
                                   {:cells [[common/list-item-thumbnail    lister-id item-dex {:thumbnail thumbnail}]
                                            [common/list-item-primary-cell lister-id item-dex {:label name :stretch? true
                                                                                               :placeholder :unnamed-vehicle}]
                                            [common/list-item-detail    lister-id item-dex {:content   timestamp :width "160px"}]
                                            [common/list-item-marker    lister-id item-dex {:icon      :navigate_next}]]}]))

(defn- vehicle-item
  [lister-id item-dex {:keys [id] :as vehicle-item}]
  [elements/toggle {:content     [vehicle-item-structure lister-id item-dex vehicle-item]
                    :hover-color :highlight
                    :on-click    [:router/go-to! (str "/@app-home/rental-vehicles/"id)]}])

(defn- z
  [lister-id item-dex {:keys [id] :as vehicle-item}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- vehicle-lister-body
  []
  [item-lister/body :rental-vehicles.lister
                    {:default-order-by :modified-at/descending
                     :items-path       [:rental-vehicles :lister/downloaded-items]
                     :error-element    [common/error-content {:error :the-content-you-opened-may-be-broken}]
                     :ghost-element    #'common/item-lister-ghost-element
                     :list-element     #'z}])

(defn- vehicle-lister-header
  []
  [common/item-lister-header :rental-vehicles.lister
                             {:cells [[common/item-lister-header-spacer :rental-vehicles.lister
                                                                        {:width "108px"}]
                                      [common/item-lister-header-cell   :rental-vehicles.lister
                                                                        {:label :name          :order-by-key :name :stretch? true}]
                                      [common/item-lister-header-cell   :rental-vehicles.lister
                                                                        {:label :last-modified :order-by-key :modified-at :width "160px"}]
                                      [common/item-lister-header-spacer :rental-vehicles.lister
                                                                        {:width "36px"}]]}])

(defn- body
  []
  [common/item-lister-wrapper :rental-vehicles.lister
                              {:body   #'vehicle-lister-body
                               :header #'vehicle-lister-header}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn create-item-button
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])
        create-vehicle-uri (str "/@app-home/rental-vehicles/create")]
       [common/item-lister-create-item-button :rental-vehicles.lister
                                              {:disabled?       lister-disabled?
                                               :create-item-uri create-vehicle-uri}]))

(defn- search-block
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])]
       [common/item-lister-search-block :rental-vehicles.lister
                                        {:disabled?         lister-disabled?
                                         :field-placeholder :search-in-rental-vehicles}]))

(defn- breadcrumbs
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])]
       [common/surface-breadcrumbs :rental-vehicles.lister/view
                                   {:crumbs [{:label :app-home :route "/@app-home"}
                                             {:label :rental-vehicles}]
                                    :disabled? lister-disabled?}]))

(defn- label
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])]
       [common/surface-label :rental-vehicles.lister/view
                             {:disabled? lister-disabled?
                              :label     :rental-vehicles}]))

(defn- header
  []
  (if-let [first-data-received? @(r/subscribe [:item-lister/first-data-received? :rental-vehicles.lister])]
          [:<> [:div {:style {:display :flex :justify-content :space-between :flex-wrap :wrap}}
                     [label]
                     [create-item-button]]
               [breadcrumbs]
               [search-block]]
          [common/item-lister-ghost-header :rental-vehicles.lister {}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :x
  (fn [_ [_ x]]
      (println (str x))))

(defn drag-btn [{:keys [attributes listeners isDragging] :as sortable-props}]
  [:div (merge {:class "contents--brand-card-drag-handle"}
              attributes listeners)
        [elements/icon {:icon :drag_indicator :style {:cursor :grab}}]])

(defn itemx [{:keys [item-props] :as props} {:keys [attributes listeners] :as sortable-props}]
    [:div {:style {:border_ "2px solid" :display "flex" :padding_ "8px"}}
      [drag-btn sortable-props]
      [vehicle-item :rental-vehicles.lister 0 item-props]])

;;
(defn- sortable-demo
  []
  [:div
    (let [items @(r/subscribe [:db/get-item [:rental-vehicles :lister/downloaded-items]])]
;;         ; [:div {:style {:display "grid" :gap "25px" :grid-template-columns "repeat(auto-fill, minmax(200px, 1fr))"}}
         [app.components.frontend.api/sortable {:items        items
                                                :item-id-key  :id
                                                :value-path   [:rental-vehicles :lister/download-items]
                                                :item-element #'itemx
                                                :on-order-changed [:x]}])])

(defn- x
  []
  [:div [sortable-demo]])

(defn- view-structure
  []
  [:<> [header]
       [elements/horizontal-separator {:size :xxl}]
       [x]
       [body]
       [footer]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
