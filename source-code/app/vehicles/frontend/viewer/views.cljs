
(ns app.vehicles.frontend.viewer.views
  (:require
    [x.app-core.api          :as a]
    [x.app-elements.api      :as elements]
    [layouts.surface-a.api   :as surface-a]
    [plugins.item-viewer.api :as item-viewer]

    [app.common.frontend.api :as common]
    [app.components.frontend.api :as comp]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

;; -----------------------------------------------------------------------------
;; ---- Overview ----

(defn- thumbnail-view [{:keys [thumbnail]}]
  (let [viewer-disabled?   @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.viewer])]
       [elements/thumbnail ::category-thumbnail
                           {:border-radius :s
                            :class :vehicles-details--thumbnail
                            :disabled?     viewer-disabled?
                            :indent        {:top :xxs :vertical :xs}
                            :uri           thumbnail}]))

(defn detail [key value]
   [:div
    [elements/label {:style {} :content key}]
    [elements/text {:content value :style {:padding "6px 0px"}}]])

(defn name-view [{:keys [name]}]
  [detail :name name])

(defn built-year-view [{:keys [built-year]}]
  [detail :built-year built-year])

(defn seat-number-view [{:keys [seat-number]}]
  [detail :seat-number seat-number])

(defn bed-number-view [{:keys [bed-number]}]
  [detail :bed-number bed-number])


(defn- overview []
  (let [vehicle @(a/subscribe [:db/get-item [:vehicles :viewer/viewed-item]])]
    [:<>
     [:div {:style {:display "flex" :gap "25px" :justify-content "center" :align-items "center"}}
      [:div {:style {:min-width "320px"}}
       [thumbnail-view vehicle]]
      [:div {:style {:width "50%"}}
         [name-view vehicle]
         [built-year-view vehicle]
         [seat-number-view vehicle]
         [bed-number-view vehicle]]]]))

;; ---- Overview ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Images ----

(defn img [url]
  [:div {:key url
         :style {:transformOrigin    "0 0"
                 :height "200px"
                 :backgroundSize     "cover"
                 :backgroundPosition "center"
                 :backgroundImage    (str "url(" url ")")
                 :border-radius "10px"}}])

(defn images []
  (let [data @(a/subscribe [:db/get-item [:vehicles :viewer/viewed-item :images]])]
    [:div {:style {:display "grid" :gap "25px" :grid-template-columns "repeat(auto-fill, minmax(200px, 1fr))"}}
     (map img data)]))

;; ---- Images ----
;; -----------------------------------------------------------------------------

(defn- ghost-view []
  [common/item-viewer-ghost-view
   :vehicles.viewer
   {:padding "0 12px"}])

(defn- tabs []
  [comp/tabs {:view-id :vehicles.viewer}
    :overview [overview]
    :images  [images]])

(defn- viewer []
  [item-viewer/body :vehicles.viewer
   {:auto-title?   true
    :item-actions  [:delete :duplicate]
    :ghost-element #'ghost-view
    :item-element  #'tabs
    :item-path     [:vehicles :viewer/viewed-item]
    :label-key     :name}])

(defn- label-bar []
  (let [vehicle-name @(a/subscribe [:db/get-item [:vehicles :viewer/viewed-item :name] :unnamed-vehicle])
        vehicle-id   @(a/subscribe [:router/get-current-route-path-param :item-id])]
       [common/item-viewer-label-bar :vehicles.viewer
         {:edit-item-uri (str "/@app-home/vehicles/"vehicle-id"/edit")
          :label         vehicle-name}]))

(defn- breadcrumbs []
  (let [vehicle-name @(a/subscribe [:db/get-item [:vehicles :viewer/viewed-item :name] :unnamed-vehicle])]
       [common/surface-breadcrumbs :contents/view
                                   {:crumbs [{:label :app-home
                                              :route "/@app-home"}
                                             {:label :vehicles
                                              :route "/@app-home/vehicles"}
                                             {:label vehicle-name}]}]))


;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn- view-structure []
  [:<> [elements/horizontal-separator {:size :s}]
       [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :s}]
       [viewer]
       [elements/horizontal-separator {:size :xxl}]])

(defn view [surface-id]
  [surface-a/layout
   surface-id
   {:content #'view-structure}])
