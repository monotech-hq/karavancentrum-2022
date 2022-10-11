
(ns app.common.frontend.credits.views
    (:require [mid-fruits.css     :as css]
              [x.app-core.api     :as a]
              [x.app-details      :as details]
              [x.app-elements.api :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn copyright-label
  ; @param (map) element-props
  ;  {:theme (keyword)(opt)
  ;    :light, :dark
  ;    Default: :light}
  ;
  ; @usage
  ;  [common/copyright-label]
  [{:keys [theme]}]
  (let [server-year          @(a/subscribe [:core/get-server-year])
        copyright-information (details/copyright-information server-year)]
       [elements/label ::copyright-label
                       {:color            (case theme :dark :invert :default)
                        :content          copyright-information
                        :font-size        :xs
                        :horizontal-align :center
                        :indent           {:bottom :xs :vertical :s}
                        :style            {:opacity ".6"}}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn mt-logo
  ; @param (map) element-props
  ;  {:theme (keyword)(opt)
  ;    :light, :dark
  ;    Default: :light}
  ;
  ; @usage
  ;  [common/mt-logo]
  [{:keys [theme]}]
  [:div {:style {:background-image (case theme :dark (css/url "/app/logo/mt-logo-dark.png")
                                                     (css/url "/app/logo/mt-logo-light.png"))
                 :background-size  "cover"
                 :height           "72px"
                 :width            "72px"}}])

(defn created-by-label
  ; @param (map) element-props
  ;  {:theme (keyword)(opt)
  ;    :light, :dark
  ;    Default: :light}
  ;
  ; @usage
  ;  [common/created-by-label]
  [{:keys [theme]}]
  [elements/label ::created-by-label
                  {:color     (case theme :dark :invert :default)
                   :content   "Created by"
                   :font-size :xs
                   :indent    {:top :xxs}}])

(defn created-by
  ; @param (map) element-props
  ;  {:theme (keyword)(opt)
  ;    :light, :dark
  ;    Default: :light}
  ;
  ; @usage
  ;  [common/created-by]
  [element-props]
  [elements/toggle ::created-by
                   {:on-click {:fx [:environment/open-new-browser-tab! "https://www.monotech.hu"]}
                    :content  [:div {:style {:align-items "center" :display "flex" :flex-direction "column"}}
                                    [mt-logo          element-props]
                                    [created-by-label element-props]]
                    :indent   {:bottom :xxs}}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn credits
  ; @param (map) element-props
  ;  {:theme (keyword)(opt)
  ;    :light, :dark
  ;    Default: :light}
  ;
  ; @usage
  ;  [common/credits]
  [element-props]
  [:div {:style {}}
        [:div {:style {:display "flex" :justify-content "center"}}
              [created-by element-props]]
        [copyright-label element-props]])
