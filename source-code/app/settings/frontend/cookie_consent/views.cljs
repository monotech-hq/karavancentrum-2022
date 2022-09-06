
(ns app.settings.frontend.cookie-consent.views
    (:require [x.app-elements.api :as elements]))

;; -- Header components -------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn got-it-button
  []
  [elements/button ::got-it-button
                   {:label    :got-it!
                    :preset   :close-button
                    :variant  :transparent
                    :on-click {:dispatch-n [[:ui/close-popup! :settings.cookie-consent/view]
                                            [:environment/cookie-settings-changed]]}}])

(defn header
  [_]
  [elements/horizontal-polarity ::header
                                {:end-content [got-it-button]}])
