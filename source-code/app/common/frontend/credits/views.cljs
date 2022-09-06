
(ns app.common.frontend.credits.views
    (:require [mid-fruits.css     :as css]
              [x.app-core.api     :as a]
              [x.app-details      :as details]
              [x.app-elements.api :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn copyright-label
  ; @usage
  ;  [common/copyright-label]
  []
  (let [server-year          @(a/subscribe [:core/get-server-year])
        copyright-information (details/copyright-information server-year)]
       [elements/label ::copyright-label
                       {:content          copyright-information
                        :font-size        :xs
                        :horizontal-align :center
                        :indent           {:bottom :xs :vertical :xs}
                        :style            {:opacity ".6"}}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn mt-logo
  ; @usage
  ;  [common/mt-logo]
  []
  [:div {:style {:background-image (css/url "/logo/mt-logo-light.png")
                 :background-size  "cover"
                 :height           "72px"
                 :width            "72px"}}])

(defn created-by-label
  ; @usage
  ;  [common/created-by-label]
  []
  [elements/label ::created-by-label
                  {:content   "Created by"
                   :font-size :xs
                   :indent    {:top :xxs}}])

(defn created-by
  ; @usage
  ;  [common/created-by]
  []
  [elements/toggle ::created-by
                   {:on-click {:fx [:environment/go-to! "https://www.monotech.hu"]}
                    :content  [:div {:style {:align-items "center" :display "flex" :flex-direction "column"}}
                                    [mt-logo]
                                    [created-by-label]]
                    :indent   {:bottom :xxs}}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn credits
  ; @usage
  ;  [common/credits]
  []
  [:div {:style {}}
        [:div {:style {:display "flex" :justify-content "center"}}
              [created-by]]
        [copyright-label]])
