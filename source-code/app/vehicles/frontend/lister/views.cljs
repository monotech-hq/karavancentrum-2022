
(ns app.vehicles.frontend.lister.views
    (:require [app.common.frontend.api :as common]
              [layouts.surface-a.api   :as surface-a]
              [plugins.item-lister.api :as item-lister]
              [x.app-core.api          :as a]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- vehicle-item-structure
  [lister-id item-dex {:keys [modified-at name thumbnail]}]
  (let [timestamp @(a/subscribe [:activities/get-actual-timestamp modified-at])]
       [common/list-item-structure lister-id item-dex
                                   {:cells [[common/list-item-thumbnail    lister-id item-dex {:thumbnail thumbnail}]
                                            [common/list-item-primary-cell lister-id item-dex {:label name :stretch? true
                                                                                               :placeholder :unnamed-vehicle}]
                                            [common/list-item-detail    lister-id item-dex {:content   timestamp :width "160px"}]
                                            [common/list-item-end-icon  lister-id item-dex {:icon      :navigate_next}]]}]))

(defn- vehicle-item
  [lister-id item-dex {:keys [id] :as vehicle-item}]
  [elements/toggle {:content     [vehicle-item-structure lister-id item-dex vehicle-item]
                    :hover-color :highlight
                    :on-click    [:router/go-to! (str "/@app-home/vehicles/"id)]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- vehicle-lister-body
  []
  [item-lister/body :vehicles.lister
                    {:default-order-by :modified-at/descending
                     :items-path       [:vehicles :lister/downloaded-items]
                     :error-element    [common/error-content {:error :the-content-you-opened-may-be-broken}]
                     :ghost-element    #'common/item-lister-ghost-element
                     :list-element     #'vehicle-item}])

(defn- vehicle-lister-header
  []
  [common/item-lister-header :vehicles.lister
                             {:cells [[common/item-lister-header-spacer :vehicles.lister
                                                                        {:width "108px"}]
                                      [common/item-lister-header-cell   :vehicles.lister
                                                                        {:label :name          :order-by-key :name :stretch? true}]
                                      [common/item-lister-header-cell   :vehicles.lister
                                                                        {:label :last-modified :order-by-key :modified-at :width "160px"}]
                                      [common/item-lister-header-spacer :vehicles.lister
                                                                        {:width "36px"}]]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn create-item-button
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :vehicles.lister])
        create-vehicle-uri (str "/@app-home/vehicles/create")]
       [common/item-lister-create-item-button :vehicles.lister
                                              {:disabled?       lister-disabled?
                                               :create-item-uri create-vehicle-uri}]))

(defn- search-block
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :vehicles.lister])]
       [common/item-lister-search-block :vehicles.lister
                                        {:disabled?         lister-disabled?
                                         :field-placeholder :search-in-vehicles}]))

(defn- breadcrumbs
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :vehicles.lister])]
       [common/surface-breadcrumbs :vehicles.lister/view
                                   {:crumbs [{:label :app-home :route "/@app-home"}
                                             {:label :vehicles}]
                                    :disabled? lister-disabled?}]))

(defn- label-bar
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :vehicles.lister])]
       [common/surface-label :vehicles.lister/view
                             {:disabled? lister-disabled?
                              :label     :vehicles}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- footer
  []
  (if-let [first-data-received? @(a/subscribe [:item-browser/first-data-received? :vehicles.lister])]
          [common/item-lister-download-info :vehicles.lister {}]))

(defn- body
  []
  [common/item-lister-wrapper :vehicles.lister
                              {:body   #'vehicle-lister-body
                               :header #'vehicle-lister-header}])

(defn- header
  []
  (if-let [first-data-received? @(a/subscribe [:item-lister/first-data-received? :vehicles.lister])]
          [:<> [:div {:style {:display :flex :justify-content :space-between :flex-wrap :wrap}}
                     [label-bar]
                     [create-item-button]]
               [breadcrumbs]
               [search-block]]
          [common/item-lister-ghost-header :vehicles.lister {}]))

(defn- view-structure
  []
  [:<> [header]
       [elements/horizontal-separator {:size :xxl}]
       [body
        [footer]]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
