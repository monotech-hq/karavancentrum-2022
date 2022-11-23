
(ns site.components.frontend.created-by-link.views
    (:require [elements.api  :as elements]
              [random.api    :as random]
              [re-frame.api  :as r]
              [x.app-details :as x.app-details]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- created-by-link
  ; @param (keyword) component-id
  ; @param (map) component-props
  ;  {:style (map)(opt)
  ;   :theme (keyword)(opt)}
  [_ {:keys [style theme]}]
  (let [server-year          @(r/subscribe [:x.core/get-server-year])
        copyright-information (x.app-details/copyright-information server-year)]
       [:div {:style (merge style {:display "flex" :gap "12px" :justify-content "center"})}
             [elements/button ::link
                              {:color     (case theme :dark :invert :default)
                               :font-size :xs
                               :on-click  {:fx [:x.environment/open-new-browser-tab! "https://www.monotech.hu"]}
                               :label     copyright-information
                               :style     {:opacity ".75"}}]]))

(defn component
  ; @param (keyword)(opt) component-id
  ; @param (map) component-props
  ;  {:style (map)(opt)
  ;   :theme (keyword)(opt)
  ;    :light, :dark
  ;    Default: :light}
  ;
  ; @usage
  ;  [created-by-link {...}]
  ;
  ; @usage
  ;  [created-by-link :my-created-by-link {...}]
  ([component-props]
   [component (random/generate-keyword) component-props])

  ([component-id component-props]
   [created-by-link component-id component-props]))
