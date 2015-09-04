package it.playfellas.superapp.ui.master;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import it.playfellas.superapp.InternalConfig;
import it.playfellas.superapp.R;
import it.playfellas.superapp.ui.BitmapUtils;

public class GameFragment extends Fragment implements
        MasterDialogFragment.DialogConfirmListener {

    private static final String TAG = GameFragment.class.getSimpleName();

    protected GamePresenter presenter;
    protected List<ImageView> imageViews = new ArrayList<>();

    @Bind(R.id.scoreTextView)
    TextView scoreTextView;
    @Bind(R.id.globalScoreTextView)
    TextView globalScoreTextView;

    //Photos taken on slave devices.
    @Bind(R.id.photo1ImageView)
    protected CircleImageView photo1ImageView;
    @Bind(R.id.photo2ImageView)
    protected CircleImageView photo2ImageView;
    @Bind(R.id.photo3ImageView)
    protected CircleImageView photo3ImageView;
    @Bind(R.id.photo4ImageView)
    protected CircleImageView photo4ImageView;

    //The central image, that represent the progress (in number of completed stages) of the current game.
    @Bind(R.id.central_img)
    ImageView centralImageView;

    private List<Bitmap> piecesList;
    protected Bitmap photoBitmap;

    /**
     * Method to init central image, creating a grayscale version of {@code photoBitmap}.
     *
     * @param numStages the maximum number of stages used to split the original bitmap.
     */
    public void initCentralImage(int numStages) {
        //split the original bitmap and store its pieces in a List
        piecesList = BitmapUtils.splitImage(photoBitmap, numStages);
        //create a gray scale version of the original bitmap
        Bitmap gray = BitmapUtils.toGrayscale(photoBitmap);
        //update the gui with the gray scale version
        centralImageView.setImageBitmap(gray);
    }

    /**
     * Method to update the central image coloring {@code currentStages} pieces,
     * and leaving {@code numStages-currentStages} pieces in gray scale.
     *
     * @param currentStage starts from 0 to numStages-1
     * @param numStages    the maximum number of stages
     */
    protected void updateStageImage(int currentStage, int numStages) {
        if (currentStage > numStages) {
            return;
        }

        Log.d("GameFragment", "currentStage: " + currentStage + " , maxStages: " + numStages);

        //Copy the arrayList of the photoBitmap's pieces
        List<Bitmap> bitmapListCopy = new ArrayList<>(piecesList);

        //update the pieces by the value of currentStages
        for (int i = 0; i < numStages; i++) {
            if (i <= currentStage) {
                bitmapListCopy.set(i, bitmapListCopy.get(i));
            } else {
                bitmapListCopy.set(i, BitmapUtils.toGrayscale(bitmapListCopy.get(i)));
            }
        }

        //get the combined image
        Bitmap finalBitmap = BitmapUtils.getCombinedBitmapByPieces(bitmapListCopy, numStages);
        //free resources for performance reasons
//        BitmapUtils.recycleBitmapList(bitmapListCopy);

        //set the combined image in the gui
        centralImageView.setImageBitmap(finalBitmap);
    }

    /**
     * Method to update the current stage's score. This is not the global score.
     *
     * @param currentStageScore The total score.
     */
    protected void setCurrentStageScore(int currentStageScore) {
        this.scoreTextView.setText(currentStageScore + "");
    }

    /**
     * Method to update the global score, non only of the current stage, but it's the sum of all stages scores.
     *
     * @param currentStageScore The score of the current stage.
     * @param maxScorePerStage  The max score that you must obtain to complete the current stage.
     * @param currentStageNum   The current stage number (0 to maxNumStages - 1).
     */
    protected void setGlobalScore(int currentStageScore, int maxScorePerStage, int currentStageNum) {
        int globalScore = (maxScorePerStage * currentStageNum) + currentStageScore;
        Log.d(TAG, "globalscore: " + globalScore);
        this.globalScoreTextView.setText(globalScore + "");
    }


    public void showDialogToProceed() {
        //show a dialog with this title and string, but this isn't a dialog to ask confirmation
        showDialogFragment("Stage completato", "Pronto per lo stage successivo?", false,
                InternalConfig.MASTER_DIAG_TAG, InternalConfig.MASTER_DIAG_ID);
    }

    @Override
    public void yesButtonPressed() {
        //if you press YES on a dialog
        presenter.beginNextStage();
    }

    @Override
    public void noButtonPressed() {
        //show a dialog with this title and string, but this time displays a dialog to ask
        //if you are really sure to confirm the previous action
        this.hideDialogFragment(InternalConfig.MASTER_DIAG_TAG);

        //now i'll show a new dialog fragment
        showDialogFragment("Terminare la partita?", "Sei sicuro di voler terminare la partita?", true,
                InternalConfig.MASTER_AREYOUSURE_DIAG_TAG, InternalConfig.MASTER_AREYOUSURE_DIAG_ID);
    }

    @Override
    public void yesButtonAreYouSurePressed() {
        //TODO cancel the game an go back to the main activity or the gameactivity.
    }

    @Override
    public void noButtonAreYouSurePressed() {
        //do nothing, because i dismiss the "Are you sure dialog"
        //and reload the dialog to proceed
        showDialogFragment("Stage completato", "Pronto per lo stage successivo?", false,
                InternalConfig.MASTER_DIAG_TAG, InternalConfig.MASTER_DIAG_ID);
    }

    private void showDialogFragment(String title, String message, boolean areYouSureDialog, String tag, int id) {
        MasterDialogFragment masterDialogFragment = (MasterDialogFragment) getFragmentManager().findFragmentByTag(tag);
        if (masterDialogFragment == null) {
            masterDialogFragment = MasterDialogFragment.newInstance(title, message, areYouSureDialog);
            masterDialogFragment.setTargetFragment(this, id);
            masterDialogFragment.show(getFragmentManager(), tag);
            getFragmentManager().executePendingTransactions();
        }
    }

    private void hideDialogFragment(String tag) {
        MasterDialogFragment masterDialogFragment = (MasterDialogFragment) getFragmentManager().findFragmentByTag(tag);
        if (masterDialogFragment != null) {
            masterDialogFragment.dismiss();
            getFragmentManager().executePendingTransactions();
        }
    }

    protected void initPhotos() {
        this.imageViews.add(this.photo1ImageView);
        this.imageViews.add(this.photo2ImageView);
        this.imageViews.add(this.photo3ImageView);
        this.imageViews.add(this.photo4ImageView);
    }

    protected void setPhotosInImageViews(List<Bitmap> playerBitmaps) {
        for (int i = 0; i < playerBitmaps.size(); i++) {
            if (this.imageViews.get(i) == null || playerBitmaps.get(i) == null) {
                Log.e(TAG, "ImageView.get(i) or playerPhoto.get(i) are null");
                continue;
            }
            this.imageViews.get(i).setImageBitmap(playerBitmaps.get(i));
        }
    }
}
