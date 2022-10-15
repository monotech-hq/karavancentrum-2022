
(ns app.settings.frontend.editor.effects
    (:require [app.settings.frontend.editor.views :as editor.views]
              [re-frame.api                       :as r :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :settings.editor/load-editor!
  [:views.blank-page/load-page! :settings.editor/view
                                {:title :settings :helper :there-is-no-available-settings}])

(r/reg-event-fx :settings.editor/render-editor!
  [:ui/render-surface! :settings.editor/view
                       {:content #'editor.views/view}])
